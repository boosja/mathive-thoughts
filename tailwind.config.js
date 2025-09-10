/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.clj"],
  // darkMode: 'selector',
  theme: {
    backgroundSize: {
      sm: '50px',
    },
    container: {
      center: true,
    },
    extend: {
      typography: theme => ({
        DEFAULT: {
          css: {
            a: {
              color: theme('colors.blue.600')
            },
            'a:hover': {
              color: theme('colors.blue.500')
            },
            code: {
              padding: '2px 4px',
              borderRadius: '4px',
              backgroundColor: theme('colors.amber.100'),
            },
            'code::before': false,
            'code::after': false,
            blockquote: {
              quotes: "none",
            },
            'pre.codehilite': {
              backgroundColor: false,
              color: false,
            },
          }
        },
        invert: {
          css: {
            color: theme('colors.slate.400'),
            code: {
              backgroundColor: theme('colors.black'),
              color: theme('colors.zinc.200'),
            }
          }
        }
      }),
      backgroundImage: {
        // 'texture-light': 'url("images/bg_light.svg")',
        // 'texture-dark': 'url("images/bg_dark.svg")'
      },
      fontFamily: {
        swanky: '"Fontdiner Swanky", serif',
      }
    },
  },
  plugins: [
    require('@tailwindcss/typography')
  ],
}

