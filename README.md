# 1_step_ahead

## Branches workflow
```
Zorgt ervoor dat je de laatste versie hebt van je branch waar je nu op zit
git pull

Maak een branch en geef het een zinvolle naam (Bv de feature waar je nu aan werkt)
git branch mijn_feature

Wissel van branch
git checkout mijn_feature

Voeg een bestand toe die je wilt committen
git add "test.txt"

Commit je bestand naar de branch met een zinvolle bericht
git commit -m "Nieuwe test.txt bestand toegevoegd"

Push je branch + commits naar github. Als je branch al op github staat doe dan alleen "git push"
git push --set-upstream origin mijn_feature

Als je wil mergen met een andere branch (Bv mijn_feature met main) doe dan eerst een pull met die branch
git pull origin main
```