package plant.testtree.camerademo.util;

import android.content.Context;
import plant.testtree.camerademo.adapter.FilterType;
import plant.testtree.camerademo.filter.BlackWhiteFilter;
import plant.testtree.camerademo.filter.BlurredFrameEffect;
import plant.testtree.camerademo.filter.BraSizeTestLeftFilter;
import plant.testtree.camerademo.filter.BraSizeTestRightFilter;
import plant.testtree.camerademo.filter.BrightnessFilter;
import plant.testtree.camerademo.filter.FillLightFilter;
import plant.testtree.camerademo.filter.FilterTypeExt;
import plant.testtree.camerademo.filter.GreenHouseFilter;
import plant.testtree.camerademo.filter.MoonLightFilter;
import plant.testtree.camerademo.filter.MultiplyFilter;
import plant.testtree.camerademo.filter.MxFaceBeautyFilter;
import plant.testtree.camerademo.filter.MxLomoFilter;
import plant.testtree.camerademo.filter.MxProFilter;
import plant.testtree.camerademo.filter.PassThroughFilter;
import plant.testtree.camerademo.filter.PastTimeFilter;
import plant.testtree.camerademo.filter.PrintingFilter;
import plant.testtree.camerademo.filter.ReminiscenceFilter;
import plant.testtree.camerademo.filter.ScalingFilter;
import plant.testtree.camerademo.filter.ShiftColorFilter;
import plant.testtree.camerademo.filter.SphereReflector;
import plant.testtree.camerademo.filter.SunnyFilter;
import plant.testtree.camerademo.filter.ToyFilter;
import plant.testtree.camerademo.filter.VignetteFilter;
import plant.testtree.camerademo.filter.beautify.BeautifyFilterA;
import plant.testtree.camerademo.filter.beautify.BeautifyFilterFUB;
import plant.testtree.camerademo.filter.beautify.BeautifyFilterFUC;
import plant.testtree.camerademo.filter.beautify.BeautifyFilterFUD;
import plant.testtree.camerademo.filter.beautify.BeautifyFilterFUE;
import plant.testtree.camerademo.filter.beautify.BeautifyFilterFUF;
import plant.testtree.camerademo.filter.imgproc.CustomizedBoxBlurFilter;
import plant.testtree.camerademo.filter.imgproc.GaussianBlurFilter;
import plant.testtree.camerademo.filter.insta.InsAmaroFilter;
import plant.testtree.camerademo.filter.insta.InsAntiqueFilter;
import plant.testtree.camerademo.filter.insta.InsBlackCatFilter;
import plant.testtree.camerademo.filter.insta.InsBrooklynFilter;
import plant.testtree.camerademo.filter.insta.InsCalmFilter;
import plant.testtree.camerademo.filter.insta.InsCoolFilter;
import plant.testtree.camerademo.filter.insta.InsCrayonFilter;
import plant.testtree.camerademo.filter.insta.InsEarlyBirdFilter;
import plant.testtree.camerademo.filter.insta.InsEmeraldFilter;
import plant.testtree.camerademo.filter.insta.InsEvergreenFilter;
import plant.testtree.camerademo.filter.insta.InsFairyTaleFilter;
import plant.testtree.camerademo.filter.insta.InsFreudFilter;
import plant.testtree.camerademo.filter.insta.InsHealthyFilter;
import plant.testtree.camerademo.filter.insta.InsHefeFilter;
import plant.testtree.camerademo.filter.insta.InsHudsonFilter;
import plant.testtree.camerademo.filter.insta.InsKevinFilter;
import plant.testtree.camerademo.filter.insta.InsLatteFilter;
import plant.testtree.camerademo.filter.insta.InsLomoFilter;
import plant.testtree.camerademo.filter.insta.InsN1977Filter;
import plant.testtree.camerademo.filter.insta.InsNashvilleFilter;
import plant.testtree.camerademo.filter.instb.InsNostalgiaFilter;
import plant.testtree.camerademo.filter.instb.InsPixarFilter;
import plant.testtree.camerademo.filter.instb.InsRiseFilter;
import plant.testtree.camerademo.filter.instb.InsRomanceFilter;
import plant.testtree.camerademo.filter.instb.InsSakuraFilter;
import plant.testtree.camerademo.filter.instb.InsSierraFilter;
import plant.testtree.camerademo.filter.instb.InsSketchFilter;
import plant.testtree.camerademo.filter.instb.InsSkinWhitenFilter;
import plant.testtree.camerademo.filter.instb.InsSunriseFilter;
import plant.testtree.camerademo.filter.instb.InsSunsetFilter;
import plant.testtree.camerademo.filter.instb.InsSutroFilter;
import plant.testtree.camerademo.filter.instb.InsSweetsFilter;
import plant.testtree.camerademo.filter.instb.InsTenderFilter;
import plant.testtree.camerademo.filter.instb.InsToasterFilter;
import plant.testtree.camerademo.filter.instb.InsValenciaFilter;
import plant.testtree.camerademo.filter.instb.InsWaldenFilter;
import plant.testtree.camerademo.filter.instb.InsWarmFilter;
import plant.testtree.camerademo.filter.instb.InsWhiteCatFilter;
import plant.testtree.camerademo.filter.instb.InsXproIIFilter;
import plant.testtree.camerademo.filter.shadertoy.AscIIArtFilter;
import plant.testtree.camerademo.filter.shadertoy.BasicDeformFilter;
import plant.testtree.camerademo.filter.shadertoy.BlueorangeFilter;
import plant.testtree.camerademo.filter.shadertoy.ChromaticAberrationFilter;
import plant.testtree.camerademo.filter.shadertoy.ContrastFilter;
import plant.testtree.camerademo.filter.shadertoy.CrackedFilter;
import plant.testtree.camerademo.filter.shadertoy.CrosshatchFilter;
import plant.testtree.camerademo.filter.shadertoy.EMInterferenceFilter;
import plant.testtree.camerademo.filter.shadertoy.EdgeDetectionFilter;
import plant.testtree.camerademo.filter.shadertoy.FastBlurFilter;
import plant.testtree.camerademo.filter.shadertoy.LegofiedFilter;
import plant.testtree.camerademo.filter.shadertoy.LichtensteinEsqueFilter;
import plant.testtree.camerademo.filter.shadertoy.MappingFilter;
import plant.testtree.camerademo.filter.shadertoy.MoneyFilter;
import plant.testtree.camerademo.filter.shadertoy.NoiseWarpFilter;
import plant.testtree.camerademo.filter.shadertoy.PixelizeFilter;
import plant.testtree.camerademo.filter.shadertoy.PolygonizationFilter;
import plant.testtree.camerademo.filter.shadertoy.RandomBlurFilter;
import plant.testtree.camerademo.filter.shadertoy.RefractionFilter;
import plant.testtree.camerademo.filter.shadertoy.TileMosaicFilter;
import plant.testtree.camerademo.filter.shadertoy.TrianglesMosaicFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.BeachFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.BrannanFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.CleanFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.CoralFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.CrispFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.FUOriginFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.FreshFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.GrassFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.InkwellFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.LolitaFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.NatureFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.PinkFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.RococoFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.RosyFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.SunsetFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.SweetyFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.UrbanFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.ValenciaFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.VintageFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.VividFilter;
import plant.testtree.camerademo.filter.xiuxiuxiu.WaldenFilter;

/* loaded from: classes.dex */
public class FilterFactory {
    public static AbsFilter createFilter(FilterType filterType, Context context) {
        switch (filterType) {
            case GRAY_SCALE:
                return new GrayScaleShaderFilter(context);
            case INVERT_COLOR:
                return new InvertColorFilter(context);
            case SPHERE_REFLECTOR:
                return new SphereReflector(context);
            case FILL_LIGHT_FILTER:
                return new FillLightFilter(context);
            case GREEN_HOUSE_FILTER:
                return new GreenHouseFilter(context);
            case BLACK_WHITE_FILTER:
                return new BlackWhiteFilter(context);
            case PAST_TIME_FILTER:
                return new PastTimeFilter(context);
            case MOON_LIGHT_FILTER:
                return new MoonLightFilter(context);
            case PRINTING_FILTER:
                return new PrintingFilter(context);
            case TOY_FILTER:
                return new ToyFilter(context);
            case BRIGHTNESS_FILTER:
                return new BrightnessFilter(context);
            case VIGNETTE_FILTER:
                return new VignetteFilter(context);
            case MULTIPLY_FILTER:
                return new MultiplyFilter(context);
            case REMINISCENCE_FILTER:
                return new ReminiscenceFilter(context);
            case SUNNY_FILTER:
                return new SunnyFilter(context);
            case MX_LOMO_FILTER:
                return new MxLomoFilter(context);
            case SHIFT_COLOR_FILTER:
                return new ShiftColorFilter(context);
            case MX_FACE_BEAUTY_FILTER:
                return new MxFaceBeautyFilter(context);
            case MX_PRO_FILTER:
                return new MxProFilter(context);
            case BRA_SIZE_TEST_LEFT:
                return new BraSizeTestLeftFilter(context);
            case BRA_SIZE_TEST_RIGHT:
                return new BraSizeTestRightFilter(context);
            case EDGE_DETECTION_FILTER:
                return new EdgeDetectionFilter(context);
            case PIXELIZE_FILTER:
                return new PixelizeFilter(context);
            case EM_INTERFERENCE_FILTER:
                return new EMInterferenceFilter(context);
            case TRIANGLES_MOSAIC_FILTER:
                return new TrianglesMosaicFilter(context);
            case LEGOFIED_FILTER:
                return new LegofiedFilter(context);
            case TILE_MOSAIC_FILTER:
                return new TileMosaicFilter(context);
            case BLUEORANGE_FILTER:
                return new BlueorangeFilter(context);
            case CHROMATIC_ABERRATION_FILTER:
                return new ChromaticAberrationFilter(context);
            case BASICDEFORM_FILTER:
                return new BasicDeformFilter(context);
            case CONTRAST_FILTER:
                return new ContrastFilter(context);
            case NOISE_WARP_FILTER:
                return new NoiseWarpFilter(context);
            case REFRACTION_FILTER:
                return new RefractionFilter(context);
            case MAPPING_FILTER:
                return new MappingFilter(context);
            case CROSSHATCH_FILTER:
                return new CrosshatchFilter(context);
            case LICHTENSTEINESQUE_FILTER:
                return new LichtensteinEsqueFilter(context);
            case ASCII_ART_FILTER:
                return new AscIIArtFilter(context);
            case MONEY_FILTER:
                return new MoneyFilter(context);
            case CRACKED_FILTER:
                return new CrackedFilter(context);
            case POLYGONIZATION_FILTER:
                return new PolygonizationFilter(context);
            case FAST_BLUR_FILTER:
                return new FastBlurFilter(context);
            case NATURE:
                return new NatureFilter(context);
            case CLEAN:
                return new CleanFilter(context);
            case VIVID:
                return new VividFilter(context);
            case FRESH:
                return new FreshFilter(context);
            case SWEETY:
                return new SweetyFilter(context);
            case ROSY:
                return new RosyFilter(context);
            case LOLITA:
                return new LolitaFilter(context);
            case SUNSET:
                return new SunsetFilter(context);
            case GRASS:
                return new GrassFilter(context);
            case CORAL:
                return new CoralFilter(context);
            case PINK:
                return new PinkFilter(context);
            case URBAN:
                return new UrbanFilter(context);
            case CRISP:
                return new CrispFilter(context);
            case VALENCIA:
                return new ValenciaFilter(context);
            case BEACH:
                return new BeachFilter(context);
            case VINTAGE:
                return new VintageFilter(context);
            case ROCOCO:
                return new RococoFilter(context);
            case WALDEN:
                return new WaldenFilter(context);
            case BRANNAN:
                return new BrannanFilter(context);
            case INKWELL:
                return new InkwellFilter(context);
            case FUORIGIN:
                return new FUOriginFilter(context);
            case AMARO:
                return new InsAmaroFilter(context);
            case ANTIQUE:
                return new InsAntiqueFilter(context);
            case BLACK_CAT:
                return new InsBlackCatFilter(context);
            case BROOKLYN:
                return new InsBrooklynFilter(context);
            case CALM:
                return new InsCalmFilter(context);
            case COOL:
                return new InsCoolFilter(context);
            case CRAYON:
                return new InsCrayonFilter(context);
            case EARLY_BIRD:
                return new InsEarlyBirdFilter(context);
            case EMERALD:
                return new InsEmeraldFilter(context);
            case EVERGREEN:
                return new InsEvergreenFilter(context);
            case FAIRY_TALE:
                return new InsFairyTaleFilter(context);
            case FREUD:
                return new InsFreudFilter(context);
            case HEALTHY:
                return new InsHealthyFilter(context);
            case HEFE:
                return new InsHefeFilter(context);
            case HUDSON:
                return new InsHudsonFilter(context);
            case KEVIN:
                return new InsKevinFilter(context);
            case LATTE:
                return new InsLatteFilter(context);
            case LOMO:
                return new InsLomoFilter(context);
            case N1977:
                return new InsN1977Filter(context);
            case NASHVILLE:
                return new InsNashvilleFilter(context);
            case NOSTALGIA:
                return new InsNostalgiaFilter(context);
            case PIXAR:
                return new InsPixarFilter(context);
            case RISE:
                return new InsRiseFilter(context);
            case ROMANCE:
                return new InsRomanceFilter(context);
            case SAKURA:
                return new InsSakuraFilter(context);
            case SIERRA:
                return new InsSierraFilter(context);
            case SKETCH:
                return new InsSketchFilter(context);
            case SKIN_WHITEN:
                return new InsSkinWhitenFilter(context);
            case SUNRISE:
                return new InsSunriseFilter(context);
            case SUNSET2:
                return new InsSunsetFilter(context);
            case SUTRO:
                return new InsSutroFilter(context);
            case SWEETS:
                return new InsSweetsFilter(context);
            case TENDER:
                return new InsTenderFilter(context);
            case TOASTER:
                return new InsToasterFilter(context);
            case VALENCIA2:
                return new InsValenciaFilter(context);
            case WALDEN2:
                return new InsWaldenFilter(context);
            case WARM:
                return new InsWarmFilter(context);
            case WHITE_CAT:
                return new InsWhiteCatFilter(context);
            case XPROII:
                return new InsXproIIFilter(context);
            case BEAUTIFY_A:
                return new BeautifyFilterA(context);
            case BEAUTIFY_FU_B:
                return new BeautifyFilterFUB(context);
            case BEAUTIFY_FU_C:
                return new BeautifyFilterFUC(context);
            case BEAUTIFY_FU_D:
                return new BeautifyFilterFUD(context);
            case BEAUTIFY_FU_E:
                return new BeautifyFilterFUE(context);
            case BEAUTIFY_FU_F:
                return new BeautifyFilterFUF(context);
            default:
                return new PassThroughFilter(context);
        }
    }

    public static AbsFilter createFilterExt(FilterTypeExt filterTypeExt, Context context) {
        switch (filterTypeExt) {
            case SCALING:
                return new ScalingFilter(context);
            case GAUSSIAN_BLUR:
                return new GaussianBlurFilter(context);
            case BLURRED_FRAME:
                return new BlurredFrameEffect(context);
            case BOX_BLUR:
                return new CustomizedBoxBlurFilter(4);
            case FAST_BLUR:
                return new FastBlurFilter(context);
            case RANDOM_BLUR:
                return new RandomBlurFilter(context);
            default:
                return null;
        }
    }
}
