LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE    := fuelsheet
LOCAL_SRC_FILES := fuelsheet.c

ifeq ($(TARGET_ARCH_ABI),armeabi-v7a)
    LOCAL_SRC_FILES += asm/arm7a/getMaior.s asm/arm7a/getMenor.s asm/arm7a/getMedia.s asm/arm7a/getTotal.s asm/arm7a/getRendimento.s asm/arm7a/getRendimentoTotal.s
else
    LOCAL_SRC_FILES += src/NativeReport.c
endif



include $(BUILD_SHARED_LIBRARY)