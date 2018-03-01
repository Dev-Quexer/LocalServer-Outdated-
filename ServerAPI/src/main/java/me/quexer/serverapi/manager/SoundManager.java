package me.quexer.serverapi.manager;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoundManager {

    private Sound NORMAL1;
    private Sound NORMAL2;
    private Sound NORMAL3;

    private Sound GOOD1;
    private Sound GOOD2;
    private Sound GOOD3;

    private Sound BAD1;
    private Sound BAD2;
    private Sound BAD3;

    private List<Sound> normalSounds;
    private List<Sound> goodSounds;
    private List<Sound> badSounds;

    public SoundManager(Sound NORMAL1, Sound NORMAL2, Sound NORMAL3, Sound GOOD1, Sound GOOD2, Sound GOOD3, Sound BAD1, Sound BAD2, Sound BAD3) {
        this.NORMAL1 = NORMAL1;
        this.NORMAL2 = NORMAL2;
        this.NORMAL3 = NORMAL3;
        this.GOOD1 = GOOD1;
        this.GOOD2 = GOOD2;
        this.GOOD3 = GOOD3;
        this.BAD1 = BAD1;
        this.BAD2 = BAD2;
        this.BAD3 = BAD3;

        normalSounds = new ArrayList<>();
        goodSounds = new ArrayList<>();
        badSounds = new ArrayList<>();

        normalSounds.add(getNORMAL1());
        normalSounds.add(getNORMAL2());
        normalSounds.add(getNORMAL3());

        goodSounds.add(getGOOD1());
        goodSounds.add(getGOOD2());
        goodSounds.add(getGOOD3());

        badSounds.add(getBAD1());
        badSounds.add(getBAD2());
        badSounds.add(getBAD3());


    }

    public void playNormal(Player p) {
        Random rdm = new Random();
        p.playSound(p.getLocation(), normalSounds.get(rdm.nextInt(normalSounds.size())), 1, 3);
    }
    public void playGood(Player p) {
        Random rdm = new Random();
        p.playSound(p.getLocation(), goodSounds.get(rdm.nextInt(goodSounds.size())), 1, 3);
    }
    public void playBad(Player p) {
        Random rdm = new Random();
        p.playSound(p.getLocation(), badSounds.get(rdm.nextInt(badSounds.size())), 1, 3);
    }


    private Sound getNORMAL1() {
        return NORMAL1;
    }

    private Sound getNORMAL2() {
        return NORMAL2;
    }


    private Sound getNORMAL3() {
        return NORMAL3;
    }


    private Sound getGOOD1() {
        return GOOD1;
    }


    private Sound getGOOD2() {
        return GOOD2;
    }



    private Sound getGOOD3() {
        return GOOD3;
    }


    private Sound getBAD1() {
        return BAD1;
    }


    private Sound getBAD2() {
        return BAD2;
    }


    private Sound getBAD3() {
        return BAD3;
    }

}
