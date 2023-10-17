LICENSE = "CLOSED"

SRCREV = "b63c3934878be75e53713c5ec1cae3d12a87088d"
SRC_URI = "git://github.com/shr-project/com.webos.app.minimal;protocol=https;branch=webpack \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json \
"
S = "${WORKDIR}/git"

DEPENDS = "nodejs-native strace-native"

#export PSEUDO_DEBUG = "nfoPcvdDyerpswikVx"

do_configure() {
    :
}

do_compile() {
    :
}

do_install() {
    DEBUG=* NODE_DEBUG_NATIVE=* NODE_DEBUG=* strace -f -v ${STAGING_BINDIR_NATIVE}/node --trace-event-categories=node.fs.sync,node.fs.async,node,v8,node.async_hooks,node.bootstrap,node.console,node.threadpoolwork.sync,node.threadpoolwork.async,node.environment,node.fs_dir.sync,node.fs_dir.async,node.promises.rejections,node.vm.script node_modules/webpack-cli/bin/cli.js -o ${D}/test
}

FILES:${PN} += "test"

# to make sure this isn't cached in sstate-cache after showing just a warning in package_qa
# http://errors.yoctoproject.org/Errors/Details/739941/
ERROR_QA:append = " host-user-contaminated"
