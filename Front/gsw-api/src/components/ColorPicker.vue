<template>
    <div>
      <v-btn id = "btn-color-picker" :color="computedSelectedColor" @click="toggleColorPicker">
        {{ computedSelectedColor }}
      </v-btn>
  
      <v-overlay
        v-model="showColorPicker"
        :style="overlayStyle"
        @click:outside="showColorPicker = false"
      >
        <v-card class="pa-4">
          <v-color-picker mode="hex" v-model="computedSelectedColor" />
        </v-card>
      </v-overlay>
  
  </div>
</template>
  
<script>
  export default {
    props: {
      selectedColor: 
      {
        type: String,
        required: false,
      },
    },
    data() {
      return {
        showColorPicker: false,       // Controls the visibility of the color picker overlay
        modelValue: '#747474',        // Initial color
        overlayStyle: {               // Style to position the overlay
          position: 'absolute',       // Ensures absolute positioning
          top: '0px',                 // Default initial values
          left: '0px'
        },
      };
    },
    methods: {
      toggleColorPicker(event)
      {
        this.showColorPicker = !this.showColorPicker;

        if (this.showColorPicker)
        {
          const button = event.currentTarget.getBoundingClientRect();
          this.overlayStyle.top = `${button.y + window.scrollY - 150}px`;
          this.overlayStyle.left = `${button.x + window.scrollX + button.width + 10}px`;
        }
      },
    },
    computed: {
      computedSelectedColor: {
        get()
        {
          this.modelValue = this.selectedColor == undefined ? "#747474" : this.selectedColor;
          return this.modelValue;
        },
        set(value)
        {
          this.modelValue = value
          this.$emit('update:modelValue', value);
        },
      },
    },
  };
</script>