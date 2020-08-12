# ZSAPIM43 - Aperam Europe's Inventory Manager 4.3 Agentry Application
SAP Inventory Manager is a mobile solution based on SAP ERP/S4HANA MM, SAP Mobile Platform / SCPms and Agentry technology. This repository holds the Agentry part of the solution.

## Repository Organization and Content

This repository contains the following projects:

* The **/agentry** folter contains the **SAP Agentry project**, which holds the application metadata (aka Agentry source code) that's used by Agentry Client to run the mobile application.

* The **/java** folder contains the **Java Backend project**, responsible for connecting to SAP Backend via JCo and perform actions using the BAPI Wrappers. It's used by the Agentry Client application whenever a Transmit operation is executed in the mobile device. The compiled jar file runs in the Agentry Server (in this case, the SMP Server) and the data is exchanged with the Agentry Client via Angel protocol.

## Versioning

The SAP Agentry project versions follows the [Semantic Versioning](https://semver.org/) standards. For example, version X.Y.Z (as in 1.1.9), where:

* X = Major version. You must change this when you implemented new features to the SAP Agentry project that breaks it's integration contract with SAP backend. This usually needs that the SAP backend to be changed as well. Creating a new major version helps us remember to syncronize the deployment of both systems when transporting changes across the landscape. For example, when new data structures are required in data exchange steps or when fields are added or removed in existing data structures.

* Y = Minor version. Use this when new features are added to the SAP Agentry project, but SAP backend contracts are not compromised. For example, when changing app screens, adding client-side validations and implementing new screens that rely on existing data structures and data exchange steps.

* Z = Patch version. Use this to fix bugs in production system. These fixes might break the SAP backend integration contract as our priority here is to fix a problem in the system.

## Repository Guidelines for Versioning

The `master` branch must always contain the latest stable source code of the application. The `development` branch must always contain the latest integrated development version of a future major release.

There's also branches named as `v_X.Y.*`. This is a release branch. This holds a freezed version of a specific major + minor release. It's used to add patches (aka bugfixes) to this release without the need to undo development work in `develop` branch.

## Repository Guidelines for Tagging

All these branches above contains the source code, required libs and additional files that can be used to build a particular release. Tagging will be used to associate a commit in a release branch (one of those with the `v_X.Y.*` pattern) to a specific X.Y.Z version release. This release will be linked to the source code at the moment of that commit and will contain also a compiled, production-ready version of the application. This version can be downloaded and directly imported into SMP via *SMP Admin -> Applications -> (app) -> Configure -> App-specific Settings -> Import feature*.

## Repository Guidelines for Development Workflow

### Working on a New Major Release

The most up-to-date source code for a new major release is stored in the `develop` branch. When adding a new feature to a future release, first create a new branch for your feature from the `develop` branch.

Example: `git checkout -b my-new-feature develop`

Then, work on your major features in the `my-new-feature` branch. Once done, commit to your `my-new-feature` local branch, then pushes it to origin `my-new-feature` and opens a pull request for merging `my-new-feature` into `develop`. Document you PR and once the merge is complete, delete your local and remote `my-new-feature` branches.

### Releasing a New Major Release

Once the new major release contained in `develop` branch it's feature-complete and stable, create a new release branch from `develop` branch. The name of this new release branch should be `v_X.0.0`, where X is a single integer increment on the last major release version.

Create a new tag and a new git release from `v_X.0.0`. Then, add the release notes and the Agentry Editor Production publishe zip file, plus all the required libs and additional files.

### Working on a New Minor Release

When you start working on a new minor release version, create a new branch named `v_X.Y.*`, where X is the current major release and Y is a single integer increment on the last minor release version.

Then, create another branch for the feature you're about to work on.

Example: `git checkout -b my-new-feature v_X.Y.*`.

Then, work on your minor features in the `my-new-feature` branch. Once done, commit to your `my-new-feature` local branch, then pushes it to origin `my-new-feature` and opens a pull request for merging back `my-new-feature` into `v_X.Y.*`. Document you PR.

Once the merge above is complete, evaluate if this new minor should be merged into the future new major. If thats the case, open a pull request for merging `my-new-feature` into `develop`. If that's not the case, delete your local and remote `my-new-feature` branches.

### Releasing a New Minor Release

Once the new minor release contained in `v_X.Y.*` branch it's feature-complete and stable, create a new tag and a new git release from `v_X.Y.0`. Then, add the release notes and the Agentry Editor Production publishe zip file, plus all the required libs and additional files.

## Working on Patches for a Existing Minor Release

First, create a new branch for your patch based on the minor release branch that you want to fix.

Example: `git checkout -b my-new-patch v_X.Y.*`.

Then, work on your patch in the `my-new-patch` branch. Once done, commit to your `my-new-patch` local branch, then pushes it to origin `my-new-patch` and opens a pull request for merging back `my-new-patch` into `v_X.Y.*`. Document you PR.

Once the merge above is complete, evaluate if there are minor release branches above the one you fixed. For example, when you patch a branch `v_1.8.*` and this repository already has `v_1.9.*`. If that's the case, evaluate if your patch should affect the higher minor releases as well. Again, if that's the case, carefully analyze if it's possible to merge or if you must manually patch them too.

Finally, verify if this patch should be merged into the new major being developed in `develop` branch. If thats the case, open a pull request for merging `my-new-patch` into `develop`.

Once all checks above are complete, delete your local and remote `my-new-patch` branches.

After the patch is applied to the minor releases, create a new tag and a new git release from `v_X.Y.Z`, where Z is a single integer increment on the last patch for that particular minor release version. Then, add the release notes and the Agentry Editor Production publishe zip file, plus all the required libs and additional files.

## Step-by-step on How to Create a New Release in Eclipse

1. Publish development version
2. Export Java project to development version
3. Publish production version
4. Copy the Java and localizations folders from development version to production version
5. Copy root files (don't copy Agentry.ini and JavaBE.ini files) from development to production version
6. Copy OverrideInfoTable folder from development to local machine. Fix the naming pattern from _Dev to _VersionNumber.
7. Copy fixed local OverrideInfoTable folder to production version
8. Either:
    1. Copy the JavaBE.ini file from previous versions (DS5, RS5, PS5), or:
    2. Fix the new JavaBE.ini version for DS5, RS5 and PS5. Copy to the corresponding production versions.
