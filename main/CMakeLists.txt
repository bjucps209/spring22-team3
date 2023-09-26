cmake_minimum_required(VERSION 2.6)

project(armsim)

option(BUILD_SHARED_LIBS "Create kernel as shared/dynamic library." ON)
option(CMAKE_WINDOWS_EXPORT_ALL_SYMBOLS "Export all non-static symbols on Win32" ON)

add_library(ask ask.cpp)

# Build test program (every time)
add_executable(test1_exe test1.cpp)

# Run test (if so requested)
enable_testing()
add_test(NAME test1
         COMMAND test1_exe)

