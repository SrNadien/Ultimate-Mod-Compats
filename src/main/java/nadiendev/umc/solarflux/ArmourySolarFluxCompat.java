package nadiendev.umc.solarflux;

import org.zeith.hammerlib.compat.base.BaseCompat;
import org.zeith.hammerlib.compat.base.CompatContext;
import org.zeith.solarflux.compat._base.SolarFluxCompat;

/** The solar panels of mushashite, joanfoite and nadienite, which exist when Ultimate Armoury is installed. */
@BaseCompat.LoadCompat(modid = "ultimatearmoury", compatType = SolarFluxCompat.class)
public class ArmourySolarFluxCompat extends UmcSolarFluxCompat {

    public ArmourySolarFluxCompat(CompatContext context) {
        super(context, "ultimatearmoury");
    }
}
