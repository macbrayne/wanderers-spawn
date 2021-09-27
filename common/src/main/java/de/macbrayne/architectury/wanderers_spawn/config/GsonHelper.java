package de.macbrayne.architectury.wanderers_spawn.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.macbrayne.architectury.wanderers_spawn.PlatformUtils;
import de.macbrayne.architectury.wanderers_spawn.Reference;
import net.minecraft.server.level.ServerPlayer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class GsonHelper {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static <T> T load(Path path, Class<T> typeOf) throws IOException, NullPointerException {
        if(!path.toFile().exists()) {
            throw new FileNotFoundException();
        }
        String json = new String(Files.readAllBytes(path));
        return Objects.requireNonNull(gson.fromJson(json, typeOf));
    }

    static <T> void write(Path path, T src) {
        try {
            Files.writeString(path, gson.toJson(src));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
