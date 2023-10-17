LICENSE = "CLOSED"

SRCREV = "b63c3934878be75e53713c5ec1cae3d12a87088d"
SRC_URI = "git://github.com/shr-project/com.webos.app.minimal;protocol=https;branch=webpack"
S = "${WORKDIR}/git"

DEPENDS = "nodejs-native"

export PSEUDO_DEBUG = "nfoPcvdDyerpswikVx"

do_configure() {
    :
}

do_compile[network] = "1"
do_compile() {
    ${STAGING_BINDIR_NATIVE}/npm install
}

do_install() {
    NODE_DEBUG=* ${STAGING_BINDIR_NATIVE}/node node_modules/webpack-cli/bin/cli.js -o ${D}/test
}

FILES:${PN} += "test"

# to make sure this isn't cached in sstate-cache after showing just a warning in package_qa
# http://errors.yoctoproject.org/Errors/Details/739941/
ERROR_QA:append = " host-user-contaminated"
