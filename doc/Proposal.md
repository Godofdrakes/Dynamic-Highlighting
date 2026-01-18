## Description

* Plugin with a sidebar that lets you define highlighting rules
    * Imagine Watchdog, but for highlighting players
* A rule can specify one or more targets
    * Targets specify who the rule applies to
* A rule can specify zero or more conditions
    * A rule is ignored if a condition is not met
* A rule can specify one or more effects
    * Effects specify what highlighting to apply to the target
* The order of rules in the list is their priority
    * The highest priority rules are at the top
    * The lowest priority rules are at the bottom
* Higher priority rules override lower priority rules
    * If two rules both specify an outline color to use the color from the higher priority rule is used

## Structure of a Highlighting Rule

* Targets
    * Players
    * Group
    * Friends
    * Clan
* Conditions
    * Distance from the Client
    * Client is in combat
    * Client is in an instance
    * Client is in The Wilderness
    * Client is in a multi-combat zone
* Effects
    * Show Player Name
    * Show Player Tile
    * Show Player Outline
    * Show Player On Map

Support for more targets could be added in the future
* Target _specifically_ your slayer partner?
* Target NPCs?

## Examples

### Slayer Partner Highlighting

Highlight if your slayer partner is out of range

* Highlight Slayer Partner (Good)
    * Targets
        * Friends
    * Conditions
        * Distance From Client
            * Distance < 30 tiles
    * Effects
        * Show Player Outline
            * Green
* Highlight Slayer Partner (Bad)
    * Targets
        * Friends
    * Conditions
        * Distance From Client
            * Distance >= 30 tiles
    * Effects
        * Show Player Outline
            * Red

### Potion Share Highlighting

Restore Potion Share, Boost Potion Share, butterflies, moths, and TOA potions affect multiple players within a distance from the player

* Highlight Tears of Elidinis Range
    * Targets
        * Group
        * Friends
        * Clan
    * Conditions
        * Client is in an instance
        * Client is in multi-combat zone
        * Distance from the client
            * Distance <= 1
    * Effects
        * Show Player Outline
            * Pink
* Highlight Hunter Mix/Potion Share Range
    * Targets
        * Group
        * Friends
        * Clan
    * Conditions
        * Client is in multi-combat zone
        * Distance from the client
            * Distance <= 2 tiles
    * Effects
        * Show Player Outline
            * Cyan

### Wilderness Highlighting

While in The Wilderness, highlight any unknown players in orange. If a player is under the client they are highlighted in red instead.

* Highlight Players Under Me
    * Targets
        * Players
    * Conditions
        * Client is in The Wilderness
        * Distance From Client
            * Distance == 0
    * Effects
        * Show Player Outline
            * Red
* Highlight Wilderness Players
    * Targets
        * Players
    * Conditions
        * Client is in The Wilderness
    * Effects
        * Show Player Outline
            * Orange
