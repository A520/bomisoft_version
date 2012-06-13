#!/bin/bash
czas=`date '+%j %d-%m-%Y'`
mkdir "/home/minecraft/.minecraft_backup/.Citizens/$czas"
czas2=`date '+%H:%M'`
cp /home/minecraft/.minecraft_server/plugins/Citizens/npc-profiles.yml "/home/minecraft/.minecraft_backup/.Citizens/$czas/$czas2-npc-profiles.yml"
