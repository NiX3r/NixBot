# NixBot
Discord bot for own server. It has multiple features starts from join/leave messages, through music DJ, to ticket system.

## Slash commands
### Status
_usage: /status_
Command for show status of Discord instance.
#### Info shown
- Total memory usage (only bot instance)
- Total CPU usage (only bot instance)
- Total usage points. Result of memory and CPU usage. Lower = better
- Time since bot instance start
- Error since bot starts
- Generate status info timestamp
### Play
_usage: /play <youtube url>_
Command to play music or videos (only audio) from youtube
### Queue
_usage: /queue [clear | view]_
Group command to view or clear queue
#### Clear
_usage: /queue clear_
Command to clear queue. This clear queue and then skip current song playing.
#### View
_usage: /queue view_
Command to view queue. Shows data:
- top 10 songs/videos in queue
- shows how many songs/videos are there in queue
- total songs/videos length
- total songs/videos count
### Skip
_usage: /skip_
Command to skip current song/video playing. If there's no song/video in a queue disconnect.
### Pause
_usage: /pause_
Command to pause current playing song/video.
### Unpause
_usage: /unpause_
Command to unpause current playing song/video.
### Volume
_usage: /volume <volume to set>_
Command to set bot audio volume. Value should be `0`-`100`.
### Dice
_usage: /dice <dice size>_
Command to roll a dice. Parameter is optional. Default dice size is 6
### Anonumous
_usage: /anonymous <message>_
Command to send anonymous message to specific channel. Messages are semi-anonymous if somebody tries to abuse this command.
