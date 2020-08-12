#!/usr/bin/env bash
# shellcheck disable=SC2009
ps | grep -i "mcast" | grep -v grep | cut -d " " -f1 | xargs kill
