# Contributing Guidelines

This follows the [Conventional Commits](https://www.conventionalcommits.org/) and [Semantic Versioning 1.0.0](https://semver.org/) for versioning system

This project will only consider commits made after April 9, 2025, effectively enforcing conventional commits going forward from that date

### New Commit Message Format:

```
<type>(<scope>): <subject>

<body>

<footer>
```

#### Sample:

```
fix(database): foreign key constraints

This commit fixes the database script in order to do that and this.

BREAKING CHANGE: The database schema has changed, requiring updates to existing data access objects

```
The `scope`, `body`, and `footer` within the commit message format is optional sometimes.

### Types

- `fix`: Bug fixes (triggers a PATCH version)
- `feat`: New features (triggers a MINOR version)
- `docs`: Documentation changes only
- `style`: Changes that don't affect the code (formatting, etc.)
- `refactor`: Code changes that neither fix bugs nor add features
- `perf`: Performance improvements
- `test`: Adding or updating tests
- `chore`: Updates to build process, tools, etc.

### Samples:

```
feat(menu): add continous menu

fix(dashboard): resolve all bugs

docs: add new comments

chore: update build tools

feat!: changes in file directory

```

# Breaking Changes

To indicate a breaking change, add a `!` after the type/scope or add `BREAKING CHANGE:` in the footer:

```
feat!: added Common Ailments Report feature!

BREAKING CHANGE: Added a new full feature that generates report about common ailments... 
```

Breaking changes trigger a MAJOR version bump. Example: v0.0.0 to v1.0.0