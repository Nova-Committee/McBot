package cn.evolvefield.mods.multi.api.instance;

import cn.evolvefield.mods.multi.common.ComponentWrapper;
import com.mojang.brigadier.Message;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

/**
 * This interface is merged into the mutable class implementing {@link Component} via mixins.
 * If you have a {@link Component} object you can cast it to {@link IMutableComponent} and use the methods in here.
 * <br>
 * Injected class for Minecraft version:
 * since 1.14: BaseComponent
 * since 1.16: MutableComponent
 */
public interface IMutableComponent extends Component {

	IMutableComponent ba$append(Component text);

	default IMutableComponent ba$append(String string) {
		return ba$append(ComponentWrapper.literal(string));
	}

	// kinda unnecessary
	default List<Component> ba$getSiblings() {
		return ((Component) (Object) this).getSiblings();
	}

	IMutableComponent ba$setStyle(Style style);

	// kinda unnecessary
	default Style ba$getStyle() {
		return ((Component) (Object) this).getStyle();
	}

	// kinda unnecessary
	default String ba$getString() {
		return ((Message) (Object) this).getString();
	}

	/**
	 * Is at least a shallow copy but is a deep copy on minecraft < 1.16
	 *
	 * @return the copy
	 */
	IMutableComponent ba$copy();
}
