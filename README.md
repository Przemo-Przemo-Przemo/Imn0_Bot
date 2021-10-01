# Imn0_Bot
A Discord bot to make fun of my horny weeb friend.

## What is this crap?!
Imn0_bot is a project made by Przemek&Przemek&Przemek group.
Conception of this idea began with us noticing that
one of our friends posts on discord as if they were a bot.
While it started out as a joke, later it became apparent that
they really do not post anything as if they were a human.
Thus we decided start this project...

## Setup
To get this bot up and running on your machine follow these steps:
- Clone the repository
- Download gradle dependencies
- Set the environment variables (described [below](#environment-variables))
- Run :)

## Environment variables
The following environment variables must be set in order to get the bot running.
If not, exceptions will be thrown and the project won't work.

| Environment variable      | Description
| ------------------------- | ----------------------------------------
| BOT_DISCORD_TOKEN         | The discord bot token (sending messages)
| BOT_TWEETPIK_KEY          | [Tweetpik](https://tweetpik.com/) api key (generating pictures of tweets) 
| BOT_TWITCH_CLIENT_SECRET  | Twitch client secret (clips of e-girls) 
| BOT_TWITCH_CLIENT_ID      | Twitch client id 
| BOT_TWITTER_KEY           | Twitter api key (LoL tweets) 
| BOT_MONGO_URI             | URI of the mognodb database (storing what to send next) 