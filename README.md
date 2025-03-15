# DeathTaunt 插件文档
 死亡惩罚插件

---



## 1. 概述

**DeathTaunt** 是一个基于 Bukkit API 的 Minecraft 插件，旨在为服务器提供死亡嘲讽功能。通过自定义的嘲讽消息，增强游戏的互动性和趣味性。

## 2. 安装步骤

1. **下载插件**：
    - 从 GitHub 或其他可信来源下载 `DeathTaunt.jar` 文件。

2. **放置插件文件**：
    - 将 `DeathTaunt.jar` 文件放入你的 Minecraft 服务器的 `plugins` 文件夹中。

3. **启动服务器**：
    - 启动或重启你的 Minecraft 服务器。
    - 插件会自动生成默认的配置文件 `config.yml`。

4. **配置插件**：
    - 根据需要编辑 `config.yml` 文件中的各项配置。

5. **重启服务器**：
    - 保存配置文件并重启服务器以应用更改。

## 3. 插件命令

- **/deathtaunt modify**
    - 修改配置信息。
    - **权限**: `deathtaunt.modify`
    - 默认: OP

- **/deathtaunt list**
    - 查看配置信息。
    - **权限**: `deathtaunt.list`
    - 默认: OP

- **/deathtaunt category**
    - 查看分类下配置信息。
    - **权限**: `deathtaunt.category`
    - 默认: OP

- **/deathtaunt reload**
    - 重新加载配置文件。
    - **权限**: `deathtaunt.reload`
    - 默认: OP

- **/deathtaunt help**
    - 显示插件帮助信息。
    - **权限**: `deathtaunt.help`
    - 默认: Default


## 4. 配置文件说明

`config.yml` 文件位于插件的 `DeathTaunt` 目录中，以下是各个配置项的说明：

### 4.1 基本配置

- **version**: 插件版本号。
- **logger**: 是否启用调试模式。
- **custom_message_prefix**: 自定义信息前缀，默认为空。`%killerPlayer%` 代表杀手玩家，`%deathPlayer%` 代表死者玩家。
- **at**: 是否启用艾特玩家功能。
    - **enable**: 是否启用艾特玩家。
    - **sound**: 艾特玩家时播放的声音。
- **config_check**: 加载插件时检查配置文件。
    - **enable**: 是否启用配置文件检查。
    - **strict_mode**: 严格模式，如果配置文件存在错误，则插件将无法启动。

### 4.2 消息配置

- **Message**: 包含多个嘲讽消息条目，每个条目由唯一的键标识（如 `'1'`, `'2'` 等）。
    - **name**: 嘲讽消息的名称。
    - **category**: 嘲讽消息的分类。
    - **change**: 嘲讽消息的权重。
    - **mode**: 嘲讽消息的显示模式。
        - `public`: 公共模式，所有人都可以看见信息。
        - `private`: 私人模式，只有双方可以看见信息。
        - `toDead`: 死者模式，只有死亡的一方可以看见信息。
    - **sound**: 播放的声音。
    - **sound_mode**: 声音播放模式。
        - `toDead`: 只有死亡的一方可以听到音效。
        - `toKiller`: 只有杀手的一方可以听到音效。
        - `all`: 双方都可以听到音效。
    - **list**: 嘲讽消息列表，支持颜色代码（如 `&a`, `&b` 等）。

#### 示例配置

```yaml
version: 1.0.1
logger: false
custom_message_prefix: ""
at:
  enable: true
  sound: entity.experience_orb.pickup
config_check:
  enable: true
  strict_mode: true
Message:
  '1':
    name: "名称1"
    category: "默认分类1"
    change: 0.1
    mode: "public"
    sound: "entity.experience_orb.pickup"
    sound_mode: toDead
    list:
      - "&a弱鸡！"
      - "&d死亡回放：%killerPlayer% → 你！"
      - "&c你死了！"
      - "&b轻松碾压你！"
      - "&e不收徒！"
  '2':
    name: "名称2"
    category: "默认分类2"
    change: 0.1
    mode: "private"
    sound: "entity.experience_orb.pickup"
    sound_mode: toKiller
    list:
      - "&a弱鸡！"
      - "&d死亡回放：%killerPlayer% → 你！"
      - "&c你死了！"
      - "&b轻松碾压你！"
      - "&e不收徒！"
  '3':
    name: "名称3"
    category: "默认分类3"
    change: 0.1
    mode: "toDead"
    sound: "entity.experience_orb.pickup"
    sound_mode: all
    list:
      - "&e%killerPlayer%的击杀让你看起来像个新手！"
      - "&e需要教学吗？问问%killerPlayer%怎么玩吧！"
      - "&d%killerPlayer%刚刚给你上了难忘的一课！"
```

