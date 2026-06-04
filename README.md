# Commons
Created: 2017-04-06 16:31 (JST)
Last updated: 2026-06-04 18:43 (JST)

`commons` is the shared common utility repository for the Flatf workspace. It contains low-level
helpers that are reused by infrastructure modules, trading services, adapters, and runtime tooling.

## Maven Coordinates

- Parent coordinates: `io.flatf:commons:1.0.1`
- Packaging: `pom`
- Java version: `21`
- Repository: `https://github.com/Flatf-Ltb/commons.git`

## Aggregated Modules

The root `pom.xml` currently aggregates these modules:

- `commons-concurrent`
- `commons-core`
- `commons-graph`
- `commons-reflect`

## Module Summary

### commons-core

Core utility types and helpers, including annotations, collections, codecs, file helpers, logging
support, numeric helpers, parameter models, sequence utilities, serialization contracts, state
helpers, system utilities, and thread utilities.

### commons-concurrent

Concurrency-focused utilities and low-allocation components. It includes queue, cache, map, counter,
and Disruptor/Agrona-related support used by higher-level runtime modules.

### commons-graph

Graph and DAG helper types used for relationship modeling, dependency traversal, and graph-related
runtime utilities.

### commons-reflect

Reflection helpers and scanner utilities used where dynamic type, method, field, or annotation
inspection is required.

## Repository Boundary

`commons` is separate from `infra`.

- Keep reusable, low-level helpers in `commons`.
- Keep persistence, serialization, transport, and external integration infrastructure in `infra`.
- Do not move `infra` modules back into this repository unless the repository split is explicitly
  reversed.

Common utility Java packages use the `io.flatf.common.*` namespace. Selected non-common packages
that currently live in `commons-core`, such as shared transport base abstractions and Swing helpers,
still use their existing `io.flatf.foundation.*` package namespaces until those packages are
explicitly migrated.

## Build

Run Maven through the workspace wrapper from the workspace root:

```powershell
.\tools\Invoke-Maven.ps1 -WorkingDirectory .\repos\commons -DskipTests compile
```

Install `commons` before building consumers such as `repos/infra` when the `commons-*` artifacts are
not already present in the local Maven repository:

```powershell
.\tools\Invoke-Maven.ps1 -WorkingDirectory .\repos\commons -DskipTests install
```

The root POM enables Java preview compilation. Keep build and test commands on Java 21.
