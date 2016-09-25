package com.example.svenscan.svenscan.services.permission;

/**
 * A ISuccessHandler implementation that only calls
 * the provided success callback once the call method
 * has been called enough times.
 *
 * Used by supporting multiple permissions at a time
 * to the PermissionManager.require method.
 */
class PermissionAggregator implements ISuccessHandler {
    private ISuccessHandler onSuccess;
    private int count;
    private int goalCount;

    PermissionAggregator(int goalCount, ISuccessHandler onSuccess) {
        this.goalCount = goalCount;
        this.count = 0;
        this.onSuccess = onSuccess;
    }

    @Override
    public void call() {
        count++;

        if (count == goalCount) {
            onSuccess.call();
        }
    }
}