package nadiendev.umc.solarflux;

import org.zeith.hammerlib.compat.base.BaseCompat;
import org.zeith.hammerlib.compat.base.CompatContext;
import org.zeith.solarflux.compat._base.SolarFluxCompat;

/** The solar panels of allthemodium, vibranium, unobtainium and piglich, which exist when Allthemodium is installed. */
@BaseCompat.LoadCompat(modid = "allthemodium", compatType = SolarFluxCompat.class)
public class AtmSolarFluxCompat extends UmcSolarFluxCompat {

    public AtmSolarFluxCompat(CompatContext context) {
        super(context, "allthemodium");
    }
}
