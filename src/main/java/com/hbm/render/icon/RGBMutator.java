package com.hbm.render.icon;

import java.awt.image.BufferedImage;

public interface RGBMutator {

    void mutate(BufferedImage image, int frame, int frameCount);
}
