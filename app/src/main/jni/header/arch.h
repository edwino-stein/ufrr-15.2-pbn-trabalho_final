#ifndef PROJETO_FINAL_ARCH_H

    #define PROJETO_FINAL_ARCH_H

    #if defined(__arm__)

        #if defined(__ARM_ARCH_7A__)

            #if defined(__ARM_NEON__)

                #if defined(__ARM_PCS_VFP)
                    #define ARCH "armeabi-v7a/NEON (hard-float)"
                #else
                    #define ARCH "armeabi-v7a/NEON"

            #endif
        #else
            #if defined(__ARM_PCS_VFP)
                #define ARCH "armeabi-v7a (hard-float)"
            #else
                #define ARCH "armeabi-v7a"
            #endif
        #endif
    #else
        #define ARCH "armeabi"
    #endif

    #elif defined(__i386__)
        #define ARCH "x86"

    #elif defined(__x86_64__)
        #define ARCH "x86_64"

    #elif defined(__mips64)
        #define ARCH "mips64"

    #elif defined(__mips__)
        #define ARCH "mips"

    #elif defined(__aarch64__)
        #define ARCH "arm64-v8a"
    #else
        #define ARCH "unknown"
    #endif

#endif
