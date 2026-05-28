#version 150

in vec2 Position;
out vec2 texCoord;

uniform mat4 ProjMat;

void main() {
    texCoord = Position;
    gl_Position = ProjMat * vec4(Position * 2.0 - 1.0, 0.0, 1.0);
}
