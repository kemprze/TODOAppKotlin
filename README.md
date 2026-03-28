# TODOApp — Kotlin & Jetpack Compose

A personal task management app for Android, built from scratch as a learning project and portfolio piece. The goal was never just a working app — it was to understand *why* things work, building each layer deliberately rather than copying boilerplate.

## Why this exists

I hate virtual keyboards. I needed something fast for capturing tasks on the run. I also needed a real project to learn Android development properly — not tutorials, but actual decisions: data models, navigation, persistence, theming, typography.

This is that project.

## What's inside

### Task management
- Add tasks through a multi-step wizard with smooth page transitions
- Fields: name, description, priority, category, due date, reminder
- Tasks persist across app restarts via Room database
- Emoji-based category system — no custom drawables needed

### Theming system
Four handcrafted Material3 color themes, each built from a real source photograph using the Material Theme Builder:

| Theme | Source | Character |
|-------|--------|-----------|
| **Scarlet** | Dark lacquer red paint | Deep crimson, dramatic |
| **Monstera** | Dark monstera leaves | Forest green, organic |
| **Amber** | Backlit amber stone | Warm gold, glowing |
| **Ink Blue** | Open ocean surface | Cold navy, quiet |

Each theme ships with full light and dark variants across all 30+ Material3 color roles.

### Typography
Six font families, user-selectable:
- **Playfair Display** — dramatic serif
- **Lora** — warm literary serif
- **Montserrat** — geometric sans-serif
- **Lato** — warm neutral sans-serif
- **Atkinson Hyperlegible** — designed for legibility, ADHD/low-vision friendly
- **Courier Prime** — monospaced typewriter wildcard

### Settings
- Theme picker with visual color swatches (no labels, just color)
- Font picker
- Dark mode: on / off / follow system
- Dynamic color: use Android 12+ wallpaper colors

All settings persist via DataStore, applied instantly across the whole app.

## Technical stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose + Material3 |
| Navigation | Navigation Compose |
| Local database | Room (with KSP) |
| Preferences | DataStore Preferences |
| Architecture | MVVM — ViewModel, Repository, StateFlow |
| Fonts | Bundled TTF/variable fonts via `res/font` |
| Min SDK | 33 (Android 13) |

## License

GNU Affero General Public License v3.0 — see [LICENSE](LICENSE) for details.
