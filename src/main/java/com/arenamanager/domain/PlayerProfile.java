package com.arenamanager.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "player_profiles")
public class PlayerProfile extends AbstractEntity {

    @Column(name = "preferred_peripheral_dpi")
    private Integer preferredPeripheralDpi;

    @Column(length = 60)
    private String mouseGripStyle;

    @Column(length = 1000)
    private String bio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false, unique = true)
    private Player player;

    protected PlayerProfile() {
    }

    public PlayerProfile(Integer preferredPeripheralDpi, String mouseGripStyle, String bio) {
        this.preferredPeripheralDpi = preferredPeripheralDpi;
        this.mouseGripStyle = mouseGripStyle;
        this.bio = bio;
    }

}
