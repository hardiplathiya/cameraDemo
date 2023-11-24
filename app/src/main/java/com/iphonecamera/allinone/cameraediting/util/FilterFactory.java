package com.iphonecamera.allinone.cameraediting.util;

import android.content.Context;

import com.iphonecamera.allinone.cameraediting.filter.BlackWhiteFilter;
import com.iphonecamera.allinone.cameraediting.filter.BlurredFrameEffect;
import com.iphonecamera.allinone.cameraediting.filter.BraSizeTestLeftFilter;
import com.iphonecamera.allinone.cameraediting.filter.BraSizeTestRightFilter;
import com.iphonecamera.allinone.cameraediting.filter.BrightnessFilter;
import com.iphonecamera.allinone.cameraediting.filter.FillLightFilter;
import com.iphonecamera.allinone.cameraediting.filter.FilterTypeExt;
import com.iphonecamera.allinone.cameraediting.filter.GrayScaleShaderFilter;
import com.iphonecamera.allinone.cameraediting.filter.GreenHouseFilter;
import com.iphonecamera.allinone.cameraediting.filter.InvertColorFilter;
import com.iphonecamera.allinone.cameraediting.filter.MoonLightFilter;
import com.iphonecamera.allinone.cameraediting.filter.MultiplyFilter;
import com.iphonecamera.allinone.cameraediting.filter.MxFaceBeautyFilter;
import com.iphonecamera.allinone.cameraediting.filter.MxLomoFilter;
import com.iphonecamera.allinone.cameraediting.filter.MxProFilter;
import com.iphonecamera.allinone.cameraediting.filter.PassThroughFilter;
import com.iphonecamera.allinone.cameraediting.filter.PastTimeFilter;
import com.iphonecamera.allinone.cameraediting.filter.PrintingFilter;
import com.iphonecamera.allinone.cameraediting.filter.ReminiscenceFilter;
import com.iphonecamera.allinone.cameraediting.filter.ScalingFilter;
import com.iphonecamera.allinone.cameraediting.filter.ShiftColorFilter;
import com.iphonecamera.allinone.cameraediting.filter.SphereReflector;
import com.iphonecamera.allinone.cameraediting.filter.SunnyFilter;
import com.iphonecamera.allinone.cameraediting.filter.ToyFilter;
import com.iphonecamera.allinone.cameraediting.filter.VignetteFilter;
import com.iphonecamera.allinone.cameraediting.filter.beautify.BeautifyFilterA;
import com.iphonecamera.allinone.cameraediting.filter.beautify.BeautifyFilterFUB;
import com.iphonecamera.allinone.cameraediting.filter.beautify.BeautifyFilterFUC;
import com.iphonecamera.allinone.cameraediting.filter.beautify.BeautifyFilterFUD;
import com.iphonecamera.allinone.cameraediting.filter.beautify.BeautifyFilterFUE;
import com.iphonecamera.allinone.cameraediting.filter.beautify.BeautifyFilterFUF;
import com.iphonecamera.allinone.cameraediting.filter.imgproc.CustomizedBoxBlurFilter;
import com.iphonecamera.allinone.cameraediting.filter.imgproc.GaussianBlurFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsAmaroFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsAntiqueFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsBlackCatFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsBrooklynFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsCalmFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsCoolFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsCrayonFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsEarlyBirdFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsEmeraldFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsEvergreenFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsFairyTaleFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsFreudFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsHealthyFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsHefeFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsHudsonFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsKevinFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsLatteFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsLomoFilter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsN1977Filter;
import com.iphonecamera.allinone.cameraediting.filter.insta.InsNashvilleFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsNostalgiaFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsPixarFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsRiseFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsRomanceFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsSakuraFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsSierraFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsSketchFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsSkinWhitenFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsSunriseFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsSunsetFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsSutroFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsSweetsFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsTenderFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsToasterFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsValenciaFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsWaldenFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsWarmFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsWhiteCatFilter;
import com.iphonecamera.allinone.cameraediting.filter.instb.InsXproIIFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.AscIIArtFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.BasicDeformFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.BlueorangeFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.ChromaticAberrationFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.ContrastFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.CrackedFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.CrosshatchFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.EMInterferenceFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.EdgeDetectionFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.FastBlurFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.LegofiedFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.LichtensteinEsqueFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.MappingFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.MoneyFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.NoiseWarpFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.PixelizeFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.PolygonizationFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.RandomBlurFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.RefractionFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.TileMosaicFilter;
import com.iphonecamera.allinone.cameraediting.filter.shadertoy.TrianglesMosaicFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.BeachFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.BrannanFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.CleanFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.CoralFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.CrispFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.FUOriginFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.FreshFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.GrassFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.InkwellFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.LolitaFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.NatureFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.PinkFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.RococoFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.RosyFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.SunsetFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.SweetyFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.UrbanFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.ValenciaFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.VintageFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.VividFilter;
import com.iphonecamera.allinone.cameraediting.filter.xiuxiuxiu.WaldenFilter;
import com.iphonecamera.allinone.cameraediting.adapter.FilterType;
import com.iphonecamera.allinone.cameraediting.filter.AbsFilter;


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
