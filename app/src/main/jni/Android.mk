LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE    := fuelsheet
LOCAL_SRC_FILES := fuelsheet.c

LOCAL_SRC_FILES += src/NativeReport.c

include $(BUILD_SHARED_LIBRARY)