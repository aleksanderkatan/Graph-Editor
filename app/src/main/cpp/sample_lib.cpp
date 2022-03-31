#include <jni.h>

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_graph_1editor_menu_MenuActivity_doubleUp(__unused JNIEnv *env, __unused jclass clazz, jint x) {
    int normal_int = x;
    return normal_int*2;
}