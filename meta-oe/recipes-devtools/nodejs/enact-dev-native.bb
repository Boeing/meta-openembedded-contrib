LICENSE = "CLOSED"

DEPENDS = "nodejs-native"

inherit native

SRC_URI = "git://github.com/enactjs/cli.git;branch=master;protocol=https;destsuffix=git/cli"
S = "${WORKDIR}/git"

PV = "6.0.1"
SRCREV = "6f04791935d250337a081e327f6f90d503fe9c19"

do_configure[noexec] = "1"

do_compile[network] = "1"
do_compile() {
    ${STAGING_BINDIR_NATIVE}/npm install -C ${S}/cli
}

do_install() {
    install -d ${D}${base_prefix}/opt
    cp -R --no-dereference --preserve=mode,links -v ${S}/* ${D}${base_prefix}/opt
}

SYSROOT_DIRS += "${base_prefix}/opt"
