package engine.sound;
//
import engine.ThreadManager;
import org.lwjgl.*;
import org.lwjgl.openal.*;
import org.lwjgl.stb.*;

import java.io.*;
import java.nio.*;
import java.util.*;

import static engine.util.IOUtil.ioResourceToByteBuffer;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC11.*;
import static org.lwjgl.openal.EXTThreadLocalContext.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.*;


/**
 * Created by Isak Wahlqvist on 4/30/2017.
 */
public class AudioManager {
    public AudioManager() {
        long device = alcOpenDevice((ByteBuffer)null);
        if (device == NULL) {
            throw new IllegalStateException("Failed to open the default device.");
        }

        ALCCapabilities deviceCaps = ALC.createCapabilities(device);

        System.out.println("OpenALC10: " + deviceCaps.OpenALC10);
        System.out.println("OpenALC11: " + deviceCaps.OpenALC11);
        System.out.println("caps.ALC_EXT_EFX = " + deviceCaps.ALC_EXT_EFX);

        if (deviceCaps.OpenALC11) {
            List<String> devices = ALUtil.getStringList(NULL, ALC_ALL_DEVICES_SPECIFIER);
            if (devices == null) {
            } else {
                for (int i = 0; i < devices.size(); i++) {
                    System.out.println(i + ": " + devices.get(i));
                }
            }
        }

        String defaultDeviceSpecifier = alcGetString(NULL, ALC_DEFAULT_DEVICE_SPECIFIER);
        System.out.println("Default device: " + defaultDeviceSpecifier);

        long context = alcCreateContext(device, (IntBuffer)null);
        alcSetThreadContext(context);
        AL.createCapabilities(deviceCaps);

        System.out.println("ALC_FREQUENCY: " + alcGetInteger(device, ALC_FREQUENCY) + "Hz");
        System.out.println("ALC_REFRESH: " + alcGetInteger(device, ALC_REFRESH) + "Hz");
        System.out.println("ALC_SYNC: " + (alcGetInteger(device, ALC_SYNC) == ALC_TRUE));
        System.out.println("ALC_MONO_SOURCES: " + alcGetInteger(device, ALC_MONO_SOURCES));
        System.out.println("ALC_STEREO_SOURCES: " + alcGetInteger(device, ALC_STEREO_SOURCES));

        try {
            while (!ThreadManager.shouldStop) {
                if(ThreadManager.create){
                    ThreadManager.soundToPlay = new Sound(ThreadManager.location, 10000);
                    ThreadManager.create = false;
                }
                if(ThreadManager.play) {
                    if(ThreadManager.soundToPlay.source == -1 || ThreadManager.soundToPlay.buffer == -1){
                        ThreadManager.soundToPlay.buffer = alGenBuffers();
                        ThreadManager.soundToPlay.source = alGenSources();
                        try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
                            ShortBuffer pcm = AudioManager.readVorbis("res/audio/" + ThreadManager.soundToPlay.fileName, 32 * 1024, info);

                            alBufferData(ThreadManager.soundToPlay.buffer, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
                        }
                        alSourcei(ThreadManager.soundToPlay.source, AL_BUFFER, ThreadManager.soundToPlay.buffer);
                        alSourcei(ThreadManager.soundToPlay.source, AL_LOOPING, AL_TRUE);
                    }
                    alSourcePlay(ThreadManager.soundToPlay.source);
                    System.out.println("started: " + ThreadManager.soundToPlay.fileName);
                    int sleepAmount;
                    ThreadManager.musicFinished = false;
                    if (ThreadManager.soundToPlay.length != -1) {
                        sleepAmount = ThreadManager.soundToPlay.length;
                    } else {
                        sleepAmount = 4000;
                    }

                    for(int i = 0; i < 1000; i++) {
                        Thread.sleep(sleepAmount/1000);
                        if(ThreadManager.shouldStop){
                            break;
                        }
                    }
                    if(!ThreadManager.shouldLoop){
                        ThreadManager.play = false;
                    }
                    ThreadManager.musicFinished = true;
                    alSourceStop(ThreadManager.soundToPlay.source);
                }
            }
        }catch(Exception e){} finally {
            alcMakeContextCurrent(NULL);
            alcDestroyContext(context);
            alcCloseDevice(device);
        }
    }

    static ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) {
        ByteBuffer vorbis;
        try {
            vorbis = ioResourceToByteBuffer(resource, bufferSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IntBuffer error   = BufferUtils.createIntBuffer(1);
        long      decoder = stb_vorbis_open_memory(vorbis, error, null);
        if (decoder == NULL) {
            throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
        }

        stb_vorbis_get_info(decoder, info);

        int channels = info.channels();

        int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

        ShortBuffer pcm = BufferUtils.createShortBuffer(lengthSamples);

        pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
        stb_vorbis_close(decoder);

        return pcm;
    }
}
