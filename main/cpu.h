#include <string>
#include "askapi.h"

using namespace std;

class decode{

    int cond(int num){

    }

};

class cpu{
private:
    //registers! They bring my heart joy
    word r0 = 0; word r1 = 0; word r2 = 0; word r3 = 0; word r4 = 0; word r5 = 0; word r6 = 0; word r7 = 0; 
    word r8 = 0; word r9 = 0; word r10 = 0; word r11 = 0; word r12 = 0; word r13 = 0; word r14 = 0; word r15 = 0;

    //this makes setting everything to 0 easy
    word *registers[16] = { &r0, &r1, &r2, &r3, &r4, &r5, &r6, &r7, &r8, &r9, &r10, &r11, &r12, &r13, &r14, &r15 };

    //our configuration variable
    ask_config configs = ac_nothing;

    // stats with a corresponding gray code assignment
    // why gray code? I want an excuse to use it, also it's my cpu, and I want to be a special little cupcake
    ask_stats stats;

public:

    // register operations
    void clearReg(){
        for (short i = 0; i < 16; i++){
            *registers[i] = 0;
        }
    }

    void setReg(short regNum, word newVal){
        *registers[regNum] = newVal;
    }

    int getReg(short regNum){
        return *registers[regNum];
    }


    //statistic operations
    void clearStats(){
        stats.instructions = 0;
        stats.loads = 0;
        stats.stores = 0;
        stats.load_misses = 0;
        stats.store_misses = 0;
    }

    void setStat(short stat, int newVal){
        switch (stat)
        {
        case 0:
            stats.instructions = newVal;
            break;

        case 1:
            stats.loads = newVal;;
            break;

        case 11:
            stats.stores = newVal;
            break;

        case 10:
            stats.load_misses = newVal;
            break;

        case 110:
            stats.store_misses = newVal;
            break;
    
        default:
            break;
        }
    }

    int getStat(short stat){
        switch (stat)
        {
        case 0:
            return stats.instructions;
            break;

        case 1:
            return stats.loads;
            break;

        case 11:
            return stats.stores;
            break;

        case 10:
            return stats.load_misses;
            break;

        case 110:
            return stats.store_misses;
            break;
    
        default:
            break;
        }
    }

    void addInstrucCycles(int val){
        stats.instructions += val;
    }


    // configuration operations
    void clearConfig(){ 
        configs = ac_nothing; 
    }

    void setConfig(ask_config_t newConfig) { 
        configs = newConfig; 
    }

    ask_config getConfig() { 
        return configs; 
    }


    //
    void decode(int instruction){

        int immediateFlag;

        int condition = instruction & 0xf0000000;

        //condition
        switch (condition){
        case 0xe0000000:
            // unconditional, no action
            break;
        
        default:
            break;
        }

        //
        int iBit = instruction & 0x02000000;

        switch (iBit){
        case 0x02000000:
            immediateFlag = 1;
            break;

        case 0x00000000:
            immediateFlag = 0;
            break;
        default:
            break;
        }

        int opcode = instruction & 0x01700000;

        // check condition, maybe make it a function later?
        switch (opcode){
        case 0x01a00000: // MOV

            int destRegister = (instruction & 0x0000f000) >> 12;
            int val = (instruction & 0x00000fff);
            setReg(destRegister, val);

            break;
        
        default:
            break;
        }

    }

};

