command_exists() {
  command -v "$@" > /dev/null 2>&1
}

mkdir_quietly () {
  mkdir -p $* > /dev/null 2> /dev/null
}

installed() {
      [ $# -lt 1 ] && return 1
      dpkg -s $* >/dev/null 2>&1
      [ $? -eq 0 ] && return 0
      return 1
}