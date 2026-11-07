Leaves 
===========

[![Leaves CI](https://github.com/LeavesMC/Leaves/actions/workflows/build.yml/badge.svg)](https://github.com/LeavesMC/Leaves/actions/workflows/leaves.yml)
[![Leaves Download](https://img.shields.io/github/downloads/LeavesMC/Leaves/total?color=0&logo=github)](https://github.com/LeavesMC/Leaves/releases/latest)
[![Discord](https://badgen.net/discord/online-members/5hgtU72w33?icon=discord&label=Discord&list=what)](https://discord.gg/5hgtU72w33)
[![QQ](https://img.shields.io/badge/QQ_Unofficial-815857713-blue)](http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=nisbmnCFeEJCcYWBQ10th4Fu99XWklH4&authKey=8VlUxSdrFCIwmIpxFQIGR8%2BXvIQ2II%2Bx2JfxuQ8amr9UKgINh%2BdXjudQfc%2FIeTO5&noverify=0&group_code=815857713)

**English** | [中文](README_cn.md)

> Fork of [Paper](https://github.com/PaperMC/Paper) aims at repairing broken vanilla properties.

This branch targets **Minecraft 26.2** and requires **Java 25**.

> You can see what we modify and fix at [here](https://docs.leavesmc.org/en/leaves/reference/configuration)

## How To (Server Admins)
Leaves use the same leavesclip(paperclip fork) jar system that Paper uses.

You can download the latest build of Leaves by going [here](https://github.com/LeavesMC/Leaves/releases/latest)

You can also [build it yourself](#building).

You can visit our [documentation](https://docs.leavesmc.org/leaves/guides/getting-started) for more information.

## How To (Plugin developers)
Leaves-API:
```kotlin
maven {
    name = "leavesmc-repo"
    url = "https://repo.leavesmc.org/snapshots/"
}

dependencies {
    compileOnly("org.leavesmc.leaves:leaves-api:26.2-R0.1-SNAPSHOT")
}
 ```

In order to use Leaves as a dependency you must [build it yourself](#building).
Each time you want to update your dependency, you must re-build Leaves.

Leaves-Server:
```kotlin
dependencies {
    compileOnly("org.leavesmc.leaves:leaves:26.2-R0.1-SNAPSHOT")
}
 ```

## Building

Install Git and JDK 25, and make sure `JAVA_HOME` points to your JDK 25 installation. The build needs access to GitHub and the Maven repositories to download upstream sources and dependencies.

After cloning this repository and checking out `codex/26.2-adaptation`, run these commands from the repository root.

**Windows (PowerShell):**

```powershell
.\gradlew.bat applyAllPatches
.\gradlew.bat createLeavesclipJar
```

**Linux / macOS:**

```bash
./gradlew applyAllPatches
./gradlew createLeavesclipJar
```

The runnable server jar is `leaves-server/build/libs/leaves-leavesclip-26.2-R0.1-SNAPSHOT-*.jar`.

## Pull Requests

See [Contributing](docs/CONTRIBUTING.md)

## Special Thanks To:

[<img src="https://user-images.githubusercontent.com/21148213/121807008-8ffc6700-cc52-11eb-96a7-2f6f260f8fda.png" alt="" width="150">](https://www.jetbrains.com)

[JetBrains](https://www.jetbrains.com/), creators of the IntelliJ IDEA, supports Leaves with one of their [Open Source Licenses](https://www.jetbrains.com/opensource/). Leaves recommend using IntelliJ IDEA as your IDE.
