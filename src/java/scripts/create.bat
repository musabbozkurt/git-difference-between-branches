@REM https://stackoverflow.com/questions/6204003/kill-a-process-by-looking-up-the-port-being-used-by-it-from-a-bat/6204329
@REM 


git --git-dir=D:\Dev\code\project-path\.git log --author="Musab Bozkurt" --pretty=format:"%h][%an][%ce][%ad][%s" > D:\Dev\code\project-path\stable.txt

@echo Finihed successfully.
