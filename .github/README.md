# CivVoxelMap

This is a 'fork' of [VoxelMap](https://modrinth.com/mod/voxelmap-updated) for 1.21.1 that's [CivMC](https://civmc.net)
legal.

## Changes

- CivMC bans Cave Mode outside the Nether so is now disabled by default. Also added a warning to the option button
  of the feature's illegality.

- CivMC bans reading entity equipment so player and mob helmets no longer appear in radar by default. Also added a
  warning to the option buttons of the feature's illegality.

- CivMC bans reading entity elevation so players and mobs are no longer darkened or faded by how far above or below they
  are from you. Also added a warning to the option button of the feature's illegality.

- Added a fair-play option to disable seeing crouched entities in your radar.

- Added a fair-play option to disable seeing invisible entities in your radar.

- Specific player/mob radar options now have their own screens, just click the cog icon.

- Prevents VoxelMap rendering its smaller/pixelated waypoint icons.

- Allows players to ESC out of choosing waypoint colours and icons.

- Entities on radar are now ordered by default with the following priority: players > hostiles > other.

- Fixes chat-waypoint errors resulting from chat replaying or other mods which print waypoints to the chat before
  VoxelMap is ready to receive them.

- Fixes VoxelMap command errors related to signed command arguments.

## Requirements

- Fabric Loader: `0.16.9` (or newer)
- [Fabric API](https://modrinth.com/mod/fabric-api): `0.110.0+1.21.3` (or newer)

## Recommended

- [ModMenu](https://modrinth.com/mod/modmenu): `v12.0.0` (or newer)

## Having issues?

- Are you using the [latest version](https://github.com/Protonull/CivVoxelMap/releases/latest)? If not, it's possible
  that the issue has already been fixed.

- Is there already a Github Issue for this problem? [Check here](https://github.com/Protonull/CivVoxelMap/issues?q=sort:updated-desc).

- Otherwise, feel free to [open an issue](https://github.com/Protonull/CivVoxelMap/issues/new?assignees=Protonull&labels=bug&template=bug_report.md&title=%5BBUG%5D+Something+went+wrong%21+Halp%21).
