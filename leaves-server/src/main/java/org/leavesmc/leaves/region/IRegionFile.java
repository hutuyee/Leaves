package org.leavesmc.leaves.region;

import ca.spottedleaf.moonrise.patches.chunk_system.storage.ChunkSystemRegionFile;
import net.minecraft.world.level.ChunkPos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;

public interface IRegionFile extends ChunkSystemRegionFile, AutoCloseable {
    Path getPath();

    DataInputStream getChunkDataInputStream(ChunkPos pos) throws IOException;

    DataOutputStream getChunkDataOutputStream(ChunkPos pos) throws IOException;

    void write(ChunkPos pos, ByteBuffer buf) throws IOException;

    void clear(ChunkPos pos) throws IOException;

    void flush() throws IOException;

    @Override
    void close() throws IOException;

    boolean doesChunkExist(ChunkPos pos) throws Exception;

    boolean hasChunk(ChunkPos pos);
}
