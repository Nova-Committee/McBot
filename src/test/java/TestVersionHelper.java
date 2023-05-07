import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TestVersionHelper {
    public static Version MINECRAFT_VERSION = null;


    @Test
    public void TestVer(){
        Optional<ModContainer> minecraftModContainer = FabricLoader.getInstance().getModContainer("minecraft");
        if (!minecraftModContainer.isPresent()) {
            throw new IllegalStateException("Minecraft not available?!?");
        }
        MINECRAFT_VERSION = minecraftModContainer.get().getMetadata().getVersion();
        String minecraftVersionString = MINECRAFT_VERSION.getFriendlyString();
        System.out.println(minecraftVersionString);
    }
}
