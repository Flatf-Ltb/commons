package com.conversantmedia.util.estimation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class PercentileFile {

    public static void main(final String[] args) throws IOException {

        for (final String arg : args) {
            final Percentile pFile = new Percentile();
            final BufferedReader br = new BufferedReader(new FileReader(arg));
            String line;
            while ((line = br.readLine()) != null) {
                final float sample = Float.parseFloat(line.trim());
                pFile.add(sample);
            }
            br.close();
            Percentile.print(System.out, arg, pFile);
        }

    }

}
