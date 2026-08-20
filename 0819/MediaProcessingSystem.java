abstract class MediaFile {
    private final String fileName;
    private final int fileSizeMB;

    MediaFile(String fileName, int fileSizeMB) {
        this.fileName = fileName;
        this.fileSizeMB = Math.max(0, fileSizeMB);
    }

    public String getFileName() {
        return fileName;
    }

    public int getFileSizeMB() {
        return fileSizeMB;
    }

    public abstract void displayInfo();
}

interface Playable {
    void play();
}

interface Compressible {
    void compress();
}

class ImageFile extends MediaFile implements Compressible {
    ImageFile(String fileName, int fileSizeMB) {
        super(fileName, fileSizeMB);
    }

    @Override
    public void displayInfo() {
        System.out.println("圖片檔案: " + getFileName() + " (" + getFileSizeMB() + "MB)");
    }

    @Override
    public void compress() {
        System.out.println("正在壓縮圖片檔案: " + getFileName());
    }
}

class AudioFile extends MediaFile implements Playable, Compressible {
    AudioFile(String fileName, int fileSizeMB) {
        super(fileName, fileSizeMB);
    }

    @Override
    public void displayInfo() {
        System.out.println("音訊檔案: " + getFileName() + " (" + getFileSizeMB() + "MB)");
    }

    @Override
    public void play() {
        System.out.println("播放音樂: " + getFileName());
    }

    @Override
    public void compress() {
        System.out.println("正在壓縮音訊檔案: " + getFileName());
    }
}

class VideoFile extends MediaFile implements Playable, Compressible {
    VideoFile(String fileName, int fileSizeMB) {
        super(fileName, fileSizeMB);
    }

    @Override
    public void displayInfo() {
        System.out.println("影片檔案: " + getFileName() + " (" + getFileSizeMB() + "MB)");
    }

    @Override
    public void play() {
        System.out.println("播放影片: " + getFileName());
    }

    @Override
    public void compress() {
        System.out.println("正在壓縮影片檔案: " + getFileName());
    }
}

public class MediaProcessingSystem {
    public static void main(String[] args) {
        MediaFile[] files = {
            new ImageFile("photo.jpg", 5),
            new AudioFile("song.mp3", 10),
            new VideoFile("movie.mp4", 1500)
        };

        for (MediaFile file : files) {
            file.displayInfo();

            if (file instanceof Playable playable) {
                playable.play();
            }
            if (file instanceof Compressible compressible) {
                compressible.compress();
            }
            System.out.println("------------------------");
        }
    }
}