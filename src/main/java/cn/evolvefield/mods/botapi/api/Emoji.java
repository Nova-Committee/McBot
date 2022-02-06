//package cn.evolvefield.mods.botapi.api;
//
//import cn.evolvefield.mods.botapi.BotApi;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.texture.SimpleTexture;
//import net.minecraft.client.renderer.texture.TextureUtil;
//import net.minecraft.client.resources.IResourceManager;
//import net.minecraft.util.ResourceLocation;
//import org.apache.commons.io.FileUtils;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.function.Predicate;
//
//public class Emoji implements Predicate<String> {
//    public static final ResourceLocation loading_texture = new ResourceLocation(BotApi.MODID, "textures/26a0.png");
//    public static final ResourceLocation noSignal_texture = new ResourceLocation(BotApi.MODID, "textures/26d4.png");
//    public static final ResourceLocation error_texture = new ResourceLocation(BotApi.MODID, "textures/26d4.png");
//
//    private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
//    public String name;
//    public List<String> strings;
//    public String location;
//    public int version = 1;
//
//    public boolean deleteOldTexture;
//
//    public SimpleTexture img;
//    public ResourceLocation resourceLocation = loading_texture;
//
//    public void checkLoad() {
//        if (img != null)
//            return;
//
//        img = new DownloadImageData(new File("BotApi/cache/" + name + "-" + version), "https://raw.githubusercontent.com/HrznStudio/BotApi/master/" + location, loading_texture);
//        resourceLocation = new ResourceLocation(BotApi.MODID, "texures/emoji/" + name + "-" + version);
//        Minecraft.getMinecraft().renderEngine.loadTexture(resourceLocation, img);
//    }
//
//    public ResourceLocation getResourceLocationForBinding() {
//        checkLoad();
//        if (deleteOldTexture) {
//            img.deleteGlTexture();
//            deleteOldTexture = false;
//        }
//        return resourceLocation;
//    }
//
//    @Override
//    public boolean test(String s) {
//        for (String text : strings)
//            if (s.equalsIgnoreCase(text))
//                return true;
//        return false;
//    }
//
//    public class DownloadImageData extends SimpleTexture {
//        private final File cacheFile;
//        private final String imageUrl;
//        private BufferedImage bufferedImage;
//        private Thread imageThread;
//        private boolean textureUploaded;
//
//        public DownloadImageData(File cacheFileIn, String imageUrlIn, ResourceLocation textureResourceLocation) {
//            super(textureResourceLocation);
//            this.cacheFile = cacheFileIn;
//            this.imageUrl = imageUrlIn;
//        }
//
//        private void checkTextureUploaded() {
//            if (!this.textureUploaded) {
//                if (this.bufferedImage != null) {
//                    if (this.textureLocation != null) {
//                        this.deleteGlTexture();
//                    }
//
//                    TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
//                    this.textureUploaded = true;
//                }
//            }
//        }
//
//        public int getGlTextureId() {
//            this.checkTextureUploaded();
//            return super.getGlTextureId();
//        }
//
//        public void setBufferedImage(BufferedImage bufferedImageIn) {
//            this.bufferedImage = bufferedImageIn;
//        }
//
//        @Override
//        public void loadTexture(IResourceManager resourceManager) throws IOException {
//            if (this.bufferedImage == null && this.textureLocation != null) {
//                super.loadTexture(resourceManager);
//            }
//            if (this.imageThread == null) {
//                if (this.cacheFile != null && this.cacheFile.isFile()) {
//                    try {
//                        this.bufferedImage = ImageIO.read(this.cacheFile);
//                    } catch (IOException ioexception) {
//                        this.loadTextureFromServer();
//                    }
//                } else {
//                    this.loadTextureFromServer();
//                }
//            }
//        }
//
//        protected void loadTextureFromServer() {
//            this.imageThread = new Thread("BotApi Texture Downloader #" + threadDownloadCounter.incrementAndGet()) {
//                @Override
//                public void run() {
//                    HttpURLConnection httpurlconnection = null;
//
//                    try {
//                        httpurlconnection = (HttpURLConnection) (new URL(DownloadImageData.this.imageUrl)).openConnection(Minecraft.getMinecraft().getProxy());
//                        httpurlconnection.setDoInput(true);
//                        httpurlconnection.setDoOutput(false);
//                        httpurlconnection.connect();
//
//                        if (httpurlconnection.getResponseCode() / 100 == 2) {
//                            int contentLength = httpurlconnection.getContentLength();
//                            BufferedImage bufferedimage;
//
//                            if (DownloadImageData.this.cacheFile != null) {
//                                FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), DownloadImageData.this.cacheFile);
//                                bufferedimage = ImageIO.read(DownloadImageData.this.cacheFile);
//                            } else {
//                                bufferedimage = TextureUtil.readBufferedImage(httpurlconnection.getInputStream());
//                            }
//
//                            DownloadImageData.this.setBufferedImage(bufferedimage);
//                        } else {
//                            Emoji.this.resourceLocation = noSignal_texture;
//                            Emoji.this.deleteOldTexture = true;
//                        }
//                    } catch (Exception exception) {
//                        Emoji.this.resourceLocation = error_texture;
//                        Emoji.this.deleteOldTexture = true;
//
//                    } finally {
//                        if (httpurlconnection != null) {
//                            httpurlconnection.disconnect();
//                        }
//                    }
//                }
//            };
//            this.imageThread.setDaemon(true);
//            this.imageThread.start();
//        }
//    }
//}