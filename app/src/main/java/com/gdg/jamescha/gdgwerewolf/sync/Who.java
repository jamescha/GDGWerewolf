package com.gdg.jamescha.gdgwerewolf.sync;

/**
 * Created by jamescha on 2/12/15.
 */
public class Who {
    private String name;
    private String region;
    private String chapter;
    private String img;
    private String character;
    private boolean isDead;

    public Who () {}

    public Who (String name, String region, String chapter,
                String img, String character, boolean isDead) {
        this.name = name;
        this.region = region;
        this.chapter = chapter;
        this.img = img;
        this.chapter = character;
        this.isDead = isDead;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getChapter() {
        return chapter;
    }

    public String getImg() {
        return img;
    }

    public String getCharacter() {
        return character;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public boolean equals (Who who) {
        return (name.equals(who.getName()) &&
                region.equals(who.getRegion()) &&
                chapter.equals(who.getRegion()) &&
                img.equals(who.getCharacter()) &&
                (isDead == who.getIsDead()));

    }
}
