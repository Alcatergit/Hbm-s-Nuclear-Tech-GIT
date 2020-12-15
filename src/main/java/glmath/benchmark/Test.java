package glmath.benchmark;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Two
 */
public class Test {

  private static class Testresult {

    public final String description;
    public final long timeNS;

    public Testresult(final String description, final long timeNS) {
      this.description = description + ":";
      this.timeNS = timeNS;
    }

    public void out(final long maxTimeNS) {
      final double percent = ((double) timeNS) / ((double) maxTimeNS) * 100.0;
      System.out.println(String.format("%-29s %15d ns %#6.2f%%", new Object[]{this.description, this.timeNS, percent}));
    }
  }
  protected final static int SIZE = 16;
  protected final static int ITERARIONS = 10_000_000;

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    long ns = System.nanoTime(), largest; // warmup
    int i, pos;
    float value;
    final LinkedList<Testresult> arrayResults = new LinkedList<>();
    final LinkedList<Testresult> bufferResults = new LinkedList<>();
    final LinkedList<Testresult> bufferDResults = new LinkedList<>();

    System.out.println("Using arrays of size " + SIZE + " with " + ITERARIONS + " iterations...");
    final float[] fArray = new float[SIZE];
    final FloatBuffer buffer = FloatBuffer.allocate(SIZE);
    if (buffer.isDirect()) {
      throw new RuntimeException("Non-direct buffer is direct!");
    }
    final FloatBuffer bufferD = ByteBuffer.allocateDirect(SIZE * 4).asFloatBuffer();
    if (bufferD.isDirect() == false) {
      throw new RuntimeException("Direct buffer is non-direct!");
    }
    final float[] helper = new float[SIZE];
    final FloatBuffer bufferHelper = FloatBuffer.allocate(SIZE);
    final FloatBuffer bufferDHelper = ByteBuffer.allocateDirect(SIZE * 4).asFloatBuffer();

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      for (pos = SIZE - 1; pos >= 0; --pos) {
        fArray[pos] = 1.0f;
      }
    }
    arrayResults.add(new Testresult("Loop-write array", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      Arrays.fill(fArray, 1.0f);
    }
    arrayResults.add(new Testresult("Arrays.fill", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      for (pos = SIZE - 1; pos >= 0; --pos) {
        helper[pos] = fArray[pos];
      }
    }
    arrayResults.add(new Testresult("Loop-read array", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      System.arraycopy(fArray, 0, helper, 0, fArray.length);
    }
    arrayResults.add(new Testresult("System.arraycopy", System.nanoTime() - ns));

    bufferHelper.put(helper);
    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      buffer.rewind();
      for (pos = SIZE - 1; pos >= 0; --pos) {
        buffer.put(1.0f);
      }
    }
    bufferResults.add(new Testresult("Loop-put buffer", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      buffer.rewind();
      for (pos = SIZE - 1; pos >= 0; --pos) {
        buffer.put(pos, 1.0f);
      }
    }
    bufferResults.add(new Testresult("Index-put buffer", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      buffer.rewind();
      buffer.put(helper);
    }
    bufferResults.add(new Testresult("Bulk-put array->buffer", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      buffer.rewind();
      buffer.put(bufferHelper);
    }
    bufferResults.add(new Testresult("Bulk-put buffer->buffer", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      buffer.rewind();
      buffer.put(bufferDHelper);
    }
    bufferResults.add(new Testresult("Bulk-put bufferD->buffer", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      buffer.rewind();
      for (pos = SIZE - 1; pos >= 0; --pos) {
        value = buffer.get();
      }
    }
    bufferResults.add(new Testresult("Loop-get buffer", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      buffer.rewind();
      for (pos = SIZE - 1; pos >= 0; --pos) {
        value = buffer.get(pos);
      }
    }
    bufferResults.add(new Testresult("Index-get buffer", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      buffer.rewind();
      buffer.get(helper);
    }
    bufferResults.add(new Testresult("Bulk-get buffer->array", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      bufferD.rewind();
      for (pos = SIZE - 1; pos >= 0; --pos) {
        bufferD.put(1.0f);
      }
    }
    bufferDResults.add(new Testresult("Loop-put bufferD", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      bufferD.rewind();
      for (pos = SIZE - 1; pos >= 0; --pos) {
        bufferD.put(pos, 1.0f);
      }
    }
    bufferDResults.add(new Testresult("Index-put bufferD", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      bufferD.rewind();
      bufferD.put(helper);
    }
    bufferDResults.add(new Testresult("Bulk-put array->bufferD", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      bufferD.rewind();
      bufferD.put(bufferHelper);
    }
    bufferDResults.add(new Testresult("Bulk-put buffer->bufferD", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      bufferD.rewind();
      bufferD.put(bufferDHelper);
    }
    bufferDResults.add(new Testresult("Bulk-put bufferD->bufferD", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      bufferD.rewind();
      for (pos = SIZE - 1; pos >= 0; --pos) {
        value = bufferD.get();
      }
    }
    bufferDResults.add(new Testresult("Loop-get bufferD", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      bufferD.rewind();
      for (pos = SIZE - 1; pos >= 0; --pos) {
        value = bufferD.get(pos);
      }
    }
    bufferDResults.add(new Testresult("Index-get bufferD", System.nanoTime() - ns));

    ns = System.nanoTime();
    for (i = ITERARIONS; i > 0; --i) {
      bufferD.rewind();
      bufferD.get(helper);
    }
    bufferDResults.add(new Testresult("Bulk-get bufferD->array", System.nanoTime() - ns));


    largest = 0;
    for (Testresult result : arrayResults) {
      largest = Math.max(largest, result.timeNS);
    }
    for (Testresult result : bufferResults) {
      largest = Math.max(largest, result.timeNS);
    }
    for (Testresult result : bufferDResults) {
      largest = Math.max(largest, result.timeNS);
    }

    System.out.println("-- Array tests -----------------------------------------");
    for (Testresult result : arrayResults) {
      result.out(largest);
    }
    System.out.println("-- Buffer tests ----------------------------------------");
    for (Testresult result : bufferResults) {
      result.out(largest);
    }
    System.out.println("-- Direct buffer tests ---------------------------------");
    for (Testresult result : bufferDResults) {
      result.out(largest);
    }
  }
}