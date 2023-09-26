// Project written by Edward Taylor(etayl261)
// For class CPS310 taught by Dr. Jueckstock
#include <stddef.h>
#include <string>

/* ArmSim Kernel/Shell Interface definition */
#include "askapi.h"
#include "cpu.h"

using namespace std;

// info strings!
string auth = "author=etayl261";
string api = "api=1.0";
string mockup = "mockup";

// This prevents the C++ compiler from throwing a fit about assigning string literals to a char*
char *simInfo[3] = {&auth[0], &api[0], &mockup[0]};

cpu proc;

char **ask_info(void) {
    return simInfo;
}

void ask_disasm(word address, word instruction, char *buff, size_t size) {
	// TODO
}

void ask_init(const struct ask_host_services *host) {
    //set all registers to 0
    proc.clearReg();

    //reset all stats for the next run
    proc.clearStats();

    //reset configuration
    proc.clearConfig();

    // beep boop weep woop
    host->log_msg("CPU initialized");
}

void ask_config_set(ask_config_t flags) {
    //capture the flag
    proc.setConfig(flags);
}

ask_config_t ask_config_get(void) { 
    return proc.getConfig(); 
}

void ask_stats_report(struct ask_stats *stats) {
    // Kowalski! Status report!
    stats->instructions = proc.getStat(0);
    stats->loads = proc.getStat(1);
    stats->stores = proc.getStat(11);
    stats->load_misses = proc.getStat(10);
    stats->store_misses = proc.getStat(110);
}

word ask_reg_get(ask_mode_t bank, int index) {

    return proc.getReg(index);
}

void ask_reg_set(ask_mode_t bank, int index, word value) {

    proc.setReg(index, value);
}

word ask_cpsr_get(void) {
    // TODO
    return 0;
}

void ask_cpsr_set(word value) {
    // TODO
}

int	ask_cpu_running(void) {
    // TODO
    return 0;
}

void ask_cpu_signal(ask_signal_t signal) {
    // TODO
}

int	ask_cpu_run(int cycles) {
    // ready for lift off
    proc.addInstrucCycles(cycles);
    return proc.getStat(0);
}

 
