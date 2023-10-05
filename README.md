# Genesis

A custom multiplatform Discord client based on the decompiled source
of Android v126.21

## Why?
Aliucord is an amazing project, but with time comes age. Aliucord's base
discord version is 126.21, which is missing core features and updates that
are present in the latest version of discord. This project aims to bring
as many of those features to a Java-like environment as possible.

### Why remake, instead of patching?

Aliucord relies on patching old discord to support new features, adding more
to the process of bootstrapping and updating the modified apk. Remaking the
app from scratch allows for updates to newer discord versions to be much less
dependent on patching the original source code and much more dependent on just
adding the feature to the app itself.

### Why multiplatform?

Allowing this app to be multiplatform allows all platforms to have a better
discord experience, 

## Differences (not extra features)
- UI Made in Kotlin Compose
  - Uses Material Design 3 and Material You
  - Uses Standard Kotlin Compose technologies (Navigation, DI, etc)
- Not compatible with Aliucord plugins
  - Whilst I would personally really like to be compatible
with aliucord plugins, it is not really possible.
- Voice/Screenshare service implemented in Rust

## Features
- [ ] Every feature included in Discord v126.21
  - [ ] Voice, Video, and Screenshare (to be implemented in Rust)
  - [ ] Proper conforming with latest api version in reguards to
basic features, such as messages, reactions, friends, guilds, etc.
  - 
  
- [ ] 

//TODO 