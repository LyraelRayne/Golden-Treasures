package net.snowshock.goldentreasures.utils;

import cpw.mods.fml.common.ModMetadata;
import net.snowshock.goldentreasures.references.ReferencesModInfo;

public class MetadataHelper {
    public static ModMetadata transformMetadata(ModMetadata meta) {
        meta.authorList.clear();
        meta.authorList.add("LyraelRayne");
        meta.authorList.add("SnowShock35");
        meta.modId = ReferencesModInfo.MOD_ID;
        meta.name = ReferencesModInfo.MOD_NAME;
        meta.version = ReferencesModInfo.VERSION;
        meta.description = ReferencesModInfo.DESC;
        meta.url = ReferencesModInfo.URL;
        meta.credits = ReferencesModInfo.CREDITS;
        meta.logoFile = ReferencesModInfo.LOGO_PATH;
        meta.autogenerated = false;
        return meta;
    }
}