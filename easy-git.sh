#!/bin/bash


git config --global core.editor "C:\\Program Files (x86)\\Notepad++\\notepad++.exe"
git fetch upstream
git merge upstream/master
git checkout --theirs .classpath
git mergetool
git commit -a
git push origin master