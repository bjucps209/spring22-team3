#include <iostream>

using namespace std;
int main(){

    int bits;
    int k = 1;

    bits = 0xe3a02030;

    int bit = bits & 0xf0000000;
    int bit2 = bits & 0x0f000000;

    cout << hex << bit << endl;
    cout << hex << bit2 << endl;
    cout << hex << bits << endl;

}