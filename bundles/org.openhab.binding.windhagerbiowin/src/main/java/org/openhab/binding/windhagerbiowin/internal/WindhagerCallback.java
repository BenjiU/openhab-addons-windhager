package org.openhab.binding.windhagerbiowin.internal;

import org.openhab.core.types.State;

public interface WindhagerCallback {
    void call(String s, State value);
}
