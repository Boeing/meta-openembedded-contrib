LICENSE = "CLOSED"

SRCREV = "ce88f5da60aa1665e37208d153656a3b7cea1b88"
SRC_URI = "git://github.com/shr-project/com.webos.app.minimal;protocol=https;branch=master"
S = "${WORKDIR}/git"

DEPENDS = "enact-dev-native nodejs-native"

export PSEUDO_DEBUG = "nfoPcvdDyerpswikVx"

do_configure() {
    :
}

do_compile[network] = "1"
do_compile() {
    ${STAGING_BINDIR_NATIVE}/npm install
}

do_install() {
    ${STAGING_BINDIR_NATIVE}/node ${STAGING_DIR_NATIVE}/opt/cli/bin/enact.js pack -o ${D}/test
}

FILES:${PN} += "test"

# to make sure this isn't cached in sstate-cache after showing just a warning in package_qa
# http://errors.yoctoproject.org/Errors/Details/739941/
ERROR_QA:append = " host-user-contaminated"
