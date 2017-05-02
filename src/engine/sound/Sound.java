package engine.sound;

import engine.util.IOUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbisInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by 18iwahlqvist on 4/30/2017.
 */
public class Sound {
    public String name;
    public int buffer, source;

    public Sound(String name, String fileName, boolean loop){
        boolean soundExists = false;
        for(Sound s : AllSounds.sounds){
            if(s.name.equals(name)){
                soundExists = true;
            }
        }
        //Test
        if(soundExists){
            System.out.println("Sound already exists");
        }else {
            this.name = name;

            buffer = alGenBuffers();

            source = alGenBuffers();


            try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
                ShortBuffer pcm = readVorbis("/res/sounds/" + fileName, 32 * 1024, info);

                alBufferData(buffer, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());

            }

            alSourcei(source, AL_BUFFER, buffer);

            if (loop) {
                alSourcei(source, AL_LOOPING, AL_TRUE);
            } else {
                alSourcei(source, AL_LOOPING, AL_FALSE);
            }

            AllSounds.sounds.add(this);
        }
    }


    public void play(){
        alSourcePlay(source);
    }

    public void stop(){
        alSourceStop(source);
    }

    public void delete(){
        alDeleteSources(source);
        alDeleteBuffers(buffer);
    }



    static ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) {
        ByteBuffer vorbis;
        try {
            vorbis = IOUtil.ioResourceToByteBuffer(resource, bufferSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IntBuffer error = BufferUtils.createIntBuffer(1);
        long decoder = stb_vorbis_open_memory(vorbis, error, null);
        if ( decoder == NULL )
            throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));

        stb_vorbis_get_info(decoder, info);

        int channels = info.channels();

        int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

        ShortBuffer pcm = BufferUtils.createShortBuffer(lengthSamples);

        pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
        stb_vorbis_close(decoder);

        return pcm;
    }

}
