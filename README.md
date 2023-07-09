# 3DX

This is the 3D engine production project.

JDK Version : `jdk 15`

![img.png](docs/README/img.png)

![img](docs/README/img_1.png)

## システム

+ `Escape` : システムが終了します。
+ `P` : スクリーンショットを撮影します

## 移動

+ `W` : 前進
+ `S` : 後進
+ `D` : 右へ移動
+ `A` : 左へ移動
+ `Space` : 上へ移動 **(正の方向にy軸移動)**
+ `Shift` : 下へ移動 **(負の方向にy軸移動)**

## 視点

  視点はマウスで動かせます。
  また、マウススクロールでズームが可能です

+ `X` : 視点リセット
+ `O` : ポリゴンのアウトラインのON/OFF
+ `Enter` : 詳細メニューの表示/非表示

## ブロック

+ `BackSpace` : カーソル上にあるブロックを削除できます
+ `Delete` : 存在するブロックを全て削除します。
+ `R` : ランダムにブロックを生成します。
+ `Tab` : ポリゴンを表示/非表示にします。

+ `右クリック` : 削除/生成モードを切り替えます

### 削除モード

  `左クリック`でキューブを削除できます。

### 生成モード

  `左クリック`でキューブに隣接した面に新しくキューブを生成できます。


# 技術関連

[3次元を２次元に投射する方法](docs/MATH/Vector.md)

`Main.class`内部の`MINIMUM_MODE`を`true`にすることで初期状態を軽量化できます。

~~~java
public static final boolean MINIMUM_MODE = false;　//デフォルトはfalse
~~~




