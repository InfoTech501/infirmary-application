#!/bin/bash

set -e

BASE_BRANCH="master"
RELEASE_BRANCH="release-staging"

echo "switching to $BASE_BRANCH and pulling latest changes"
git checkout "$BASE_BRANCH"
git pull origin "$BASE_BRANCH"

if git show-ref --verify --quiet refs/heads/$RELEASE_BRANCH; then
    echo "Release staging branch exists, updating..."
    git checkout "$RELEASE_BRANCH"

    echo "Resetting release staging branch to master"
    git reset --hard "$BASE_BRANCH"
else
    echo "Creating new release staging branch: $RELEASE_BRANCH"
    git checkout -b "$RELEASE_BRANCH"
fi

git clean -fd

echo "Running semantic-release dry-run mode to determine next version"
NEXT_VERSION=$(npx semantic-release --dry-run 2>&1 | grep -oP "The next release version is \K[^\s]+" | head -1 || echo "")

if [ -z "$NEXT_VERSION" ]; then
    echo "No release needed based on conventional commits!!"
    git checkout "$BASE_BRANCH"
    exit 0
fi

echo "Next version determined: $NEXT_VERSION"

echo "Preparing release files for version $NEXT_VERSION..."

if [ -f "pom.xml" ]; then
    echo "Updating Maven version to $NEXT_VERSION"
    mvn versions:set -DnewVersion="$NEXT_VERSION" -DgenerateBackupPoms=false
fi

echo "Creating changelog..."
echo "## [$NEXT_VERSION] - $(date +%Y-%m-%d)" | cat - CHANGELOG.md > temp && mv temp CHANGELOG.md

echo "staging pom.xml and CHANGELOG.md"
git add pom.xml
git add CHANGELOG.md

echo "Files staged for commit:"
git diff --staged --name-only

if git diff --staged --quiet; then
    echo "No relevant changes to stage for release $NEXT_VERSION (pom.xml/CHANGELOG.md)"
    git checkout "$BASE_BRANCH"
    exit 0
else
    echo "Committing release staging changes for $NEXT_VERSION"
    git commit -m "chore(release-staging): prepare $NEXT_VERSION

- Update version to $NEXT_VERSION
- Update CHANGELOG.md
- Ready for release review

[skip ci]"
fi

echo "Pushing release staging branch to origin"
git push origin "$RELEASE_BRANCH" --force-with-lease

echo "Managing the Pull Request"

echo " Release staging complete!"
echo "   - Branch: $RELEASE_BRANCH"
echo "   - Version: $NEXT_VERSION"
echo "   - Status: ready for review!!"

git checkout "$BASE_BRANCH"