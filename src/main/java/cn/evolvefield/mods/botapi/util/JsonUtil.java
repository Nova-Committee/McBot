package cn.evolvefield.mods.botapi.util;

import com.google.gson.*;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/20 10:32
 * Version: 1.0
 */
public class JsonUtil {
    private static final JsonUtil instance = new JsonUtil();
    private static final JsonParser parser = new JsonParser();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * @return Current JsonParser instance;
     */
    public static JsonParser getParser() {
        return parser;
    }

    /**
     * @return Current gson instance used by JsonUtil;
     */
    public static Gson getGson() {
        return gson;
    }

    /**
     * Gets an JsonObject from given String
     *
     * @param string
     * @return empty JsonObject if String == null
     */
    public static JsonObject getObjectFromString(String string) {
        JsonElement elm = getFromString(string);
        if (elm == null) {
            return new JsonObject();
        }
        return elm.getAsJsonObject();
    }

    /**
     * Gets an JsonElement from given String,<br>
     *
     * @param string
     * @return null if String == null
     */
    public static JsonElement getFromString(String string, boolean b) {
        if (string == null) {
            return null;
        }
        try {
            return parser.parse(string);
        } catch (Exception e) {
            if (b) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static JsonElement getFromString(String string) {
        return getFromString(string, true);
    }

    public static void addToArrayIfNotContains(JsonArray array, String string) {
        boolean contains = false;
        for (JsonElement elm : array) {
            if (elm.getAsString().equals(string)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            array.add(new JsonPrimitive(string));
        }
    }

    public static JsonObject getJsonForPacket(String target) {
        JsonObject obj = new JsonObject();
        obj.addProperty("target_listener", target);
        return obj;
    }

    /**
     * @param file      the file to be parsed into json
     * @param print_err notify in logs about errors while parsing or file missing
     * @return JsonElement from given file, or null
     */
    public static JsonElement read(File file, boolean print_err) {
        return read(file, print_err, null);
    }

    /**
     * @param file      the file to be parsed into json
     * @param print_err notify in logs about errors while parsing or file missing
     * @param def       default element to return in case there are errors while parsing
     * @return JsonElement from the given file, can be null
     */
    public static JsonElement read(File file, boolean print_err, @Nullable JsonElement def) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileReader fr = new FileReader(file);
            JsonElement obj = parser.parse(fr);
            fr.close();
            return obj;
        } catch (Exception e) {
            if (print_err) {
                e.printStackTrace();
            }
            LogUtil.console("File '" + file + "' seems to be missing, or has invalid format.");
            return def;
        }
    }

    /**
     * @param file the file to be parsed into json
     * @return JsonObject from file or a new JsonObject if there are errors in previous/super methods
     */
    public static JsonObject get(File file) {
        JsonElement e = read(file, false);
        if (e == null || !e.isJsonObject()) {
            return new JsonObject();
        } else return e.getAsJsonObject();
    }

    /**
     * Writes a JsonObject into the specified file (using Gson Pretty Printing)
     *
     * @param file  target file
     * @param obj   JsonElement to be written into the file
     * @param check check if the parent file/folder exists
     */
    public static void write(File file, JsonElement obj, boolean check) {
        try {
            if (check) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
            }
            FileWriter fw = new FileWriter(file);
            fw.write(gson.toJson(obj));
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a JsonObject into the specified file (using Gson Pretty Printing)
     *
     * @param file target file
     * @param obj  JsonElement to be written into the file
     */
    public static void write(File file, JsonElement obj) {
        write(file, obj, false);
    }

    /**
     * @param file   File to be updated
     * @param string Target value/object
     * @param value
     */
    public static void update(File file, String string, String value) {
        JsonObject obj = get(file);
        obj.addProperty(string, value);
        write(file, obj);
    }

    /**
     * @param file   File to be updated
     * @param string Target value/object
     * @param value
     */
    public static void update(File file, String string, boolean value) {
        JsonObject obj = get(file);
        obj.addProperty(string, value);
        write(file, obj);
    }

    /**
     * @param file   File to be updated
     * @param string Target value/object
     * @param value
     */
    public static void update(File file, String string, Number value) {
        JsonObject obj = get(file);
        obj.addProperty(string, value);
        write(file, obj);
    }

    /**
     * @param file    File to be updated
     * @param string  Target value/object
     * @param element
     */
    public static void update(File file, String string, JsonElement element) {
        JsonObject obj = get(file);
        obj.add(string, element);
        write(file, obj);
    }

    /**
     * Gets a value from a JsonObject if it exists, else returns specified default value and adds it to the JsonObject
     *
     * @param obj
     * @param target
     * @param default_value
     */
    public static String getIfExists(JsonObject obj, String target, Object default_value) {
        if (!obj.has(target)) {
            obj.addProperty(target, default_value.toString());
            return default_value.toString();
        }
        return obj.get(target).getAsString();
    }

    /**
     * Gets a value from a JsonObject if it exists, else returns specified default value and adds it to the JsonObject
     *
     * @param obj
     * @param target
     * @param default_value
     */
    public static Number getIfExists(JsonObject obj, String target, Number default_value) {
        if (!obj.has(target)) {
            obj.addProperty(target, default_value);
            return default_value;
        }
        return obj.get(target).getAsNumber();
    }

    /**
     * Gets a value from a JsonObject if it exists, else returns specified default value and adds it to the JsonObject
     *
     * @param obj
     * @param target
     * @param default_value
     */
    public static boolean getIfExists(JsonObject obj, String target, boolean default_value) {
        if (!obj.has(target)) {
            obj.addProperty(target, default_value);
            return default_value;
        }
        return obj.get(target).getAsBoolean();
    }

    public static boolean getIfExists(JsonObject obj, String[] targets, boolean default_value) {
        for (String str : targets) {
            if (obj.has(str)) {
                return obj.get(str).getAsBoolean();
            }
        }
        obj.addProperty(targets[0], default_value);
        return default_value;
    }

    /**
     * Gets a JsonElement from a JsonObject if it exists, else returns specified default JsonElement and adds it to the JsonObject
     *
     * @param obj
     * @param target
     * @param default_value
     */
    public static JsonElement getIfExists(JsonObject obj, String target, JsonElement default_value) {
        if (!obj.has(target)) {
            obj.add(target, default_value);
            return default_value;
        }
        return obj.get(target);
    }

    /**
     * Gets a value from a JsonObject if it exists, else returns null
     *
     * @param obj
     * @param target
     */
    public static String getStringIfExists(JsonObject obj, String target) {
        if (obj.has(target)) {
            return obj.get(target).getAsString();
        }
        return null;
    }

    /**
     * Gets a value from a JsonObject if it exists, else returns -1
     *
     * @param obj
     * @param target
     */
    public static Number getNumberIfExists(JsonObject obj, String target) {
        if (obj.has(target)) {
            return obj.get(target).getAsNumber();
        }
        return -1;
    }

    /**
     * Gets a value from a JsonObject if it exists, else returns false
     *
     * @param obj
     * @param target
     */
    public static boolean getBooleanIfExists(JsonObject obj, String target) {
        if (obj.has(target)) {
            return obj.get(target).getAsBoolean();
        }
        return false;
    }

    /**
     * Gets a JsonElement from a JsonObject if it exists, else returns null
     *
     * @param obj
     * @param target
     */
    public static JsonElement getIfExists(JsonObject obj, String target) {
        if (!obj.has(target)) {
            return obj.get(target);
        }
        return null;
    }

    public static JsonElement getElementIfExists(JsonObject obj, String target, boolean b) {
        JsonElement elm = getIfExists(obj, target);
        if (elm == null && b) {
            return new JsonObject();
        }
        return elm;
    }

    ////>>>> ARRAYS >>>>////

    /**
     * Gets a sub-array from an Object in a File, returns new JsonArray if there are errors in previous methods or doesn't exists.
     *
     * @param file
     * @param string
     * @return
     */
    public static JsonArray getArray(File file, String string) {
        JsonObject obj = get(file);
        if (obj.get(string) != null) {
            JsonElement el = obj.get(string);
            if (el.isJsonArray() == true) {
                return obj.get(string).getAsJsonArray();
            } else {
                return new JsonArray();
            }
        } else {
            return new JsonArray();
        }
    }


    public static boolean contains(JsonArray array, String string) {
        boolean result = false;
        for (JsonElement e : array) {
            try {
                if (e.getAsString().equals(string)) {
                    result = true;
                }
            } catch (ClassCastException ex) {
                ex.printStackTrace();
                result = false;
                continue;
            }
        }
        return result;
    }

    /**
     * @param file   File to be updated
     * @param string Target value/object
     * @param value
     */
    public static void update(File file, String string, JsonArray value) {
        JsonObject obj = get(file);
        obj.add(string, value);
        write(file, obj);
    }

    /**
     * Checks if JsonArray contains String.
     *
     * @param array
     * @param number
     * @return
     */
    public static boolean contains(JsonArray array, Number number) {
        boolean result = false;
        for (JsonElement e : array) {
            try {
                if (e.getAsNumber() == number) {
                    result = true;
                }
            } catch (ClassCastException ex) {
                ex.printStackTrace();
                result = false;
                continue;
            }
        }
        return result;
    }

    public static ArrayList<String> jsonArrayToStringArray(JsonArray array) {
        ArrayList<String> list = new ArrayList<String>();
        if (array == null) {
            return list;
        }
        for (JsonElement e : array) {
            try {
                if (e.isJsonPrimitive()) {
                    String str = e.getAsString();
                    list.add(str);
                }
            } catch (ClassCastException ex) {
                //ex.printStackTrace();
                continue;
            }
        }
        return list;
    }

    public static ArrayList<UUID> jsonArrayToUUIDArray(JsonArray array) {
        ArrayList<UUID> list = new ArrayList<UUID>();
        ArrayList<String> json = jsonArrayToStringArray(array);
        for (String string : json) {
            try {
                list.add(UUID.fromString(string));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public static ArrayList<ResourceLocation> jsonArrayToResourceLocationArray(JsonArray array) {
        ArrayList<ResourceLocation> list = new ArrayList<ResourceLocation>();
        ArrayList<String> json = jsonArrayToStringArray(array);
        for (String string : json) {
            try {
                list.add(new ResourceLocation(string));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static ArrayList<Class<?>> jsonArrayToClassArray(JsonArray array) {
        ArrayList<Class<?>> list = new ArrayList<Class<?>>();
        ArrayList<String> json = jsonArrayToStringArray(array);
        for (String string : json) {
            try {
                list.add(Class.forName(string.replace(".class", "")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static ArrayList<Integer> jsonArrayToIntegerArray(JsonArray array) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (JsonElement e : array) {
            try {
                if (e.isJsonPrimitive()) {
                    Integer num = e.getAsInt();
                    list.add(num);
                }
            } catch (ClassCastException ex) {
                //ex.printStackTrace();
                continue;
            }
        }
        return list;
    }

    public static void add(JsonArray array, String string) {
        array.add(new JsonPrimitive(string));
    }

    public static void add(JsonArray array, Number number) {
        array.add(new JsonPrimitive(number));
    }

    public static JsonArray getArrayFromStringList(ArrayList<String> array) {
        JsonArray ja = new JsonArray();
        for (String s : array) {
            add(ja, s);
        }
        return ja;
    }

    public static JsonElement getArrayFromUUIDList(ArrayList<UUID> array) {
        JsonArray ja = new JsonArray();
        for (UUID s : array) {
            add(ja, s.toString());
        }
        return ja;
    }

    public static JsonElement getArrayFromResourceLocationList(ArrayList<ResourceLocation> array) {
        JsonArray ja = new JsonArray();
        for (ResourceLocation s : array) {
            add(ja, s.toString());
        }
        return ja;
    }

    public static JsonElement getArrayFromObjectList(ArrayList<?> array) {
        JsonArray ja = new JsonArray();
        for (Object obj : array) {
            add(ja, obj.toString());
        }
        return ja;
    }

    public static JsonArray getArrayFromIntegerList(ArrayList<Integer> array) {
        JsonArray ja = new JsonArray();
        for (int i : array) {
            add(ja, i);
        }
        return ja;
    }

    public static boolean isJsonFile(File file) {
        try {
            FileReader fr = new FileReader(file);
            parser.parse(fr);
            fr.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static JsonUtil getInstance() {
        return instance;
    }

    /**
     * Transforms the InputStream into a String and then parses it into a JsonObject;
     * <BR>Does also close the InputStream.
     *
     * @param stream
     * @return
     */
    public static JsonObject getObjectFromInputStream(InputStream stream) {
        JsonElement elm = getElementFromInputStream(stream);
        return elm == null || !elm.isJsonObject() ? null : elm.getAsJsonObject();
    }

    public static JsonElement getElementFromInputStream(InputStream stream) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            String input;
            StringBuffer response = new StringBuffer();
            while ((input = in.readLine()) != null) response.append(input);
            in.close();
            return JsonUtil.getFromString(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String setPrettyPrinting(JsonObject obj) {
        return gson.toJson(obj);
    }

    public static JsonArray toJsonArray(float[] numbers) {
        JsonArray array = new JsonArray();
        for (float number : numbers) array.add(number);
        return array;
    }

    public static JsonArray toJsonArray(double[] numbers) {
        JsonArray array = new JsonArray();
        for (double number : numbers) array.add(number);
        return array;
    }

    public static JsonArray toJsonArray(int[] numbers) {
        JsonArray array = new JsonArray();
        for (int number : numbers) array.add(number);
        return array;
    }

    public static JsonArray toJsonArray(byte[] numbers) {
        JsonArray array = new JsonArray();
        for (byte number : numbers) array.add(number);
        return array;
    }

    public static JsonArray toJsonArray(short[] numbers) {
        JsonArray array = new JsonArray();
        for (short number : numbers) array.add(number);
        return array;
    }

    public static JsonArray toJsonArray(char[] numbers) {
        JsonArray array = new JsonArray();
        for (char number : numbers) array.add(number);
        return array;
    }

    public static JsonArray toJsonArray(long[] numbers) {
        JsonArray array = new JsonArray();
        for (long number : numbers) array.add(number);
        return array;
    }

    public static JsonArray toJsonArray(Number[] numbers) {
        JsonArray array = new JsonArray();
        for (Number number : numbers) array.add(number);
        return array;
    }

    public static float[] getFloatArray(JsonArray array) {
        float[] numbers = new float[array.size()];
        for (int i = 0; i < numbers.length; i++) numbers[i] = array.get(i).getAsFloat();
        return numbers;
    }

    public static double[] getDoubleArray(JsonArray array) {
        double[] numbers = new double[array.size()];
        for (int i = 0; i < numbers.length; i++) numbers[i] = array.get(i).getAsDouble();
        return numbers;
    }

    public static int[] getIntegerArray(JsonArray array) {
        int[] numbers = new int[array.size()];
        for (int i = 0; i < numbers.length; i++) numbers[i] = array.get(i).getAsInt();
        return numbers;
    }

    public static byte[] getByteArray(JsonArray array) {
        byte[] numbers = new byte[array.size()];
        for (int i = 0; i < numbers.length; i++) numbers[i] = array.get(i).getAsByte();
        return numbers;
    }

    public static short[] getShortArray(JsonArray array) {
        short[] numbers = new short[array.size()];
        for (int i = 0; i < numbers.length; i++) numbers[i] = array.get(i).getAsShort();
        return numbers;
    }

    public static char[] getCharArray(JsonArray array) {
        char[] numbers = new char[array.size()];
        for (int i = 0; i < numbers.length; i++) numbers[i] = array.get(i).getAsCharacter();
        return numbers;
    }

    public static long[] getLongArray(JsonArray array) {
        long[] numbers = new long[array.size()];
        for (int i = 0; i < numbers.length; i++) numbers[i] = array.get(i).getAsLong();
        return numbers;
    }

    public static Number[] getNumberArray(JsonArray array) {
        Number[] numbers = new Number[array.size()];
        for (int i = 0; i < numbers.length; i++) numbers[i] = array.get(i).getAsNumber();
        return numbers;
    }
}
